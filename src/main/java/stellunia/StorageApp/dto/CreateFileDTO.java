package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// DTO for whenever a new file is created
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateFileDTO {
    public UUID userId;
    public String fileName;
    public String fileContent;
    public long fileSize;
    //public String folderName;
}
