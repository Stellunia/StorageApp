package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.folder.StorageFolder;

@Data
@AllArgsConstructor
public class FileResponseDTO {
        private String fileName;
        private String downloadUrl;
        private String fileType;
        private long size;
        private String storageFolder;

        public static FileResponseDTO fromModel(StorageFile storageFile) {
                return new FileResponseDTO(
                        storageFile.getFileId().toString(),
                        storageFile.getFileName(),
                        storageFile.getFileType(),
                        storageFile.getFileData().length,
                        storageFile.getStorageFolder().getFolderName()
                );
        }
}
