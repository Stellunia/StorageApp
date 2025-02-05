package stellunia.StorageApp.file;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.folder.StorageFolderService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/storageapp/files")
public class StorageFileController {

    @Autowired
    private StorageFileService storageFileService;

    @Autowired
    private StorageFolderService storageFolderService; // this is null

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile multipartFile,
                                             @RequestParam("folder")String storageFolder) {
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageFileNotFoundException("Cannot upload empty file.");
            }

            StorageFolder folderToStore = storageFolderService.getFolderByName(storageFolder); // here is where it fails?
            StorageFile storageFile = storageFileService.uploadFile(multipartFile, folderToStore);

            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/storageapp/files/download/")
                    .path(storageFile.getFileId().toString())
                    .toUriString();

            return ResponseEntity.ok(new FileResponseDTO(
                    storageFile.getFileName(),
                    downloadUrl,
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    storageFile.getStorageFolder().getFolderName()
            ));
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        Optional<StorageFile> fileOptional = storageFileService.getFile(id);

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

    @GetMapping("/listFiles")
    public Stream<FileResponseDTO> getFiles() {
        return storageFileService.getAllFiles().stream().map(FileResponseDTO::fromModel);
    }

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
