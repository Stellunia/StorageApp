package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import stellunia.StorageApp.file.StorageFile;

@Data
@AllArgsConstructor
public class FileUploadResponseDTO {
        private String fileName;
        private String downloadUrl;
        private String fileType;
        private long size;

        public static FileUploadResponseDTO fromModel(StorageFile storageFile) {
                return new FileUploadResponseDTO(
                        storageFile.getFileId().toString(),
                        storageFile.getFileName(),
                        storageFile.getFileType(),
                        storageFile.getFileData().length
                        //storageFile.getStorageUser()
                );
        }
}
