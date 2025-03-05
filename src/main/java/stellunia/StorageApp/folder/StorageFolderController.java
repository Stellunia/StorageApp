package stellunia.StorageApp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.dto.FolderResponseDTO;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.file.StorageFileService;
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.StorageUserService;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/storageapp/storageuser/folder")
@RequiredArgsConstructor
public class StorageFolderController {

    private final StorageFolderService storageFolderService;
    private final StorageFileService storageFileService;
    private final StorageUserService storageUserService;

    /*public ResponseEntity<FileResponseDTO> uploadFile(@RequestParam("file")MultipartFile multipartFile,
                                                        @RequestParam("folder")String storageFolder) {*/

    // Handles the creation of folders
    // Requires parameter "folderName" to allow for proper creation of a folder
    @PostMapping("/createFolder")
    public ResponseEntity<?> createFolder(@RequestParam("folderName")String storageFolderName,
                                          @RequestParam String storageUser) {
        try {
            //StorageUser doesUserExist = storageUserService.getUserByName(storageUser);
            StorageFolder storageFolder = storageFolderService.createFolder(storageFolderName, storageUser);
            return ResponseEntity.ok(FolderResponseDTO.fromModel(storageFolder));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    // Handles outputting a folder and a list of all its files
    // Requires parameter "folderName" to allow for searching for a specific folder
    // Making this ADMIN-only cause holy shit it's driving me up the wall
    @GetMapping("/admin/listFolder")
    public ResponseEntity<?> getFolder(@RequestParam("folderName")String folderName/*,
                                        @RequestParam("userId")String userId*/) {
        try {
            StorageFolder storageFolder = storageFolderService.getFolderByName(folderName);
            return ResponseEntity.ok(new FolderResponseDTO(storageFolder.getFolderId(), storageFolder.getFolderName(),
                    storageFolder.getStorageFiles().stream().map(FileResponseDTO::fromModel).toList(),
                    storageFolder.getStorageUser().getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Replacement for /listFolders to let the user search for folders that are under their ID
    @GetMapping("/userFolders")
    public Stream<FolderResponseDTO> getUserFolders(
            @RequestParam("userId")String userId) {
        return storageFolderService.getUserFolders(userId).stream().map(FolderResponseDTO::fromModel);
    }

    // Handles outputting a list of all folders and their respective files - ADMIN nonsense
    @GetMapping("/admin/listFolders")
    public Stream<FolderResponseDTO> getAllFolders() {
        return storageFolderService.getAllFolders().stream().map(FolderResponseDTO::fromModel);
    }

}
