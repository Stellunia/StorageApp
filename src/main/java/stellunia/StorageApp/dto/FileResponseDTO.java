package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import stellunia.StorageApp.file.StorageFile;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FileResponseDTO {
    private String fileName;
    private String username;
    private UUID userId;
    //private String folderName;

    public static FileResponseDTO fromModel(StorageFile storageFile) {
        return new FileResponseDTO(
                storageFile.getFileName(),
                storageFile.getStorageUser().getUsername(),
                storageFile.getStorageUser().getId()/*,
                    file.getFolder().getUsername()*/
        );
    }
}