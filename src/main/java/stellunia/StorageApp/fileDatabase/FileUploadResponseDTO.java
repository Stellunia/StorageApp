package stellunia.StorageApp.fileDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponseDTO {
        private String fileName;
        private String downloadUrl;
        private String fileType;
        private long size;
}
