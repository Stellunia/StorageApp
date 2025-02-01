/*package stellunia.StorageApp.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import stellunia.StorageApp.file.FileController;
import stellunia.StorageApp.storageUser.User;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<?> createFolder(
            @RequestBody CreateFolderDTO createFolder,
            @AuthenticationPrincipal User storageUser;
    ) {
        try {
            Folder folder = folderService.createFolder(createFolder.);
            return ResponseEntity.ok(FolderResponseDTO.fromModel(post));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    public static class CreateFolderDTO {
        public String username;
        public boolean isDirectory;
    }

    public static class FolderResponseDTO {
        private UUID folderId;
        private String username;
        private long size;
        private boolean isDirectory;
        private UUID userId;
        private String username;
        private List<FileController.FileResponseDTO> files;

    }

}*/
