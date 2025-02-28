package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.folder.StorageFolder;

import java.util.List;
import java.util.UUID;

// DTO for whenever a folder is called and requires printing
@Data
@AllArgsConstructor
@Getter
public class FolderResponseDTO {
    private UUID folderId;
    private String folderName;
    //private UUID userId;
    private List<FileResponseDTO> files;
    private String username;

    public static FolderResponseDTO fromModel(StorageFolder storageFolder) {
        return new FolderResponseDTO(
                storageFolder.getFolderId(),
                storageFolder.getFolderName(),
                storageFolder.getStorageFiles().stream().map(FileResponseDTO::fromModel).toList(),
                storageFolder.getStorageUser().getUsername()
        );
    }
}
