package stellunia.StorageApp.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.folder.StorageFolderService;
import stellunia.StorageApp.user.StorageUserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/storageapp/storageuser/files")
public class StorageFileController {

    @Autowired
    private StorageFileService storageFileService;

    @Autowired
    private StorageFolderService storageFolderService; // this is null - fixed

    @Autowired
    private StorageUserService storageUserService;

    // Handles file upload through the use of two parameters:
    // "file" that is requires a MultipartFile upload
    // "folder" that requires a user input for a valid folder
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile multipartFile,
                                             @RequestParam("folder")String storageFolder,
                                             @RequestParam("user") String storageUser/*,
                                             @RequestParam("loginSessionID")String sessionId*/) { // Needs a login session ID?
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageFileNotFoundException("Cannot upload empty file.");
            }

            StorageFolder folderToStore = storageFolderService.getFolderByName(storageFolder); // here is where it fails? - changed .toString() to instead get the folder name
            //StorageUser ownerOfFile = storageUserService.getUserByName(storageUser);
            StorageFile storageFile = storageFileService.uploadFile(multipartFile, folderToStore, storageUser);

            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/storageapp/storageuser/files/download/")
                    .path(storageFile.getFileId().toString())
                    .toUriString();

            return ResponseEntity.ok(new FileResponseDTO(
                    storageFile.getFileName(),
                    downloadUrl,
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    storageFile.getStorageFolder().getFolderName(),
                    storageFile.getStorageUser().getUsername()
            ));
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Handles file download of valid files
    // {id} requires the file id to output into the API's window and allow for subsequent download
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        Optional<StorageFile> fileOptional = storageFileService.getFileById(id);

        if (fileOptional.isPresent()) {
            StorageFile storageFile = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storageFile.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, storageFile.getFileType())
                    //.header(HttpHeaders.CONTENT_LOCATION, "Folder: " + storageFile.getStorageFolder())
                    .body(storageFile.getFileData());
        }

        return ResponseEntity.notFound().build();
    }

    // Handles outputting a list of all files that exists
    @GetMapping("/admin/listFiles")
    public Stream<FileResponseDTO> getFiles() {
        return storageFileService.getAllFiles().stream().map(FileResponseDTO::fromModel);
    }

    @GetMapping("/searchFile")
    public Optional<StorageFile> getFileByFileAndUserId(@PathVariable UUID fileId,
                                                        @RequestParam UUID userId) {
        return storageFileService.getFileById(fileId);
    }

    // Replacement for /listFiles to let the user search for files that are designated in their ID
    @GetMapping("/userFiles")
    public Stream<FileResponseDTO> getUserFiles(
            @RequestParam("userId")String userId) {
        return storageFileService.getUserFiles(userId).stream().map(FileResponseDTO::fromModel);
    }

    // Handles deletion of specific files
    // {id} is the UUID of the file to be deleted
    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable UUID id){
        try {
            storageFileService.deleteFile(id);
            return ResponseEntity.ok().body("File with ID: " + id + " deleted.");
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting file: " + id + ".\nWith error message: " + e.getMessage());
        }
    }
}
