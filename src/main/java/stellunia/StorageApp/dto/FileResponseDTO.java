package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import stellunia.StorageApp.file.StorageFile;

// DTO for whenever a file is called and requires printing properly
@Data
@AllArgsConstructor
public class FileResponseDTO extends RepresentationModel<FileResponseDTO> {
        private String fileId;
        private String fileName;
        private String fileType;
        private long size;
        private String storageFolder;
        private String storageUser;

        public static FileResponseDTO fromModel(StorageFile storageFile) {
                return new FileResponseDTO(
                        storageFile.getFileId().toString(),
                        storageFile.getFileName(),
                        storageFile.getFileType(),
                        storageFile.getFileData().length,
                        storageFile.getStorageFolder().getFolderName(),
                        storageFile.getStorageUser().getUsername()
                );
        }
}
