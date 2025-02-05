package stellunia.StorageApp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.dto.FolderResponseDTO;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.file.StorageFileService;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("storageapp/folder")
@RequiredArgsConstructor
public class StorageFolderController {

    private final StorageFolderService storageFolderService;
    private final StorageFileService storageFileService;

    /*public ResponseEntity<FileResponseDTO> uploadFile(@RequestParam("file")MultipartFile multipartFile,
                                                        @RequestParam("folder")String storageFolder) {*/
    @PostMapping("/createFolder")
    public ResponseEntity<?> createFolder(@RequestParam("folderName")String storageFolderName/*,
                                          @RequestParam("storageUser")String storageUserName*/) {
        try {
            StorageFolder storageFolder = storageFolderService.createFolder(storageFolderName);
            return ResponseEntity.ok(FolderResponseDTO.fromModel(storageFolder));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @GetMapping("/listFolder")
    public ResponseEntity<?> getFolder(@RequestParam("folderName")String folderName) {
        try {
            StorageFolder storageFolder = storageFolderService.getFolderByName(folderName);
            return ResponseEntity.ok(new FolderResponseDTO(storageFolder.getFolderId(), storageFolder.getFolderName(),
                    storageFolder.getStorageFiles().stream().map(FileResponseDTO::fromModel).toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listFolders")
    public Stream<FolderResponseDTO> getAllFolders() {
        return storageFolderService.getAllFolders().stream().map(FolderResponseDTO::fromModel);
    }

}
