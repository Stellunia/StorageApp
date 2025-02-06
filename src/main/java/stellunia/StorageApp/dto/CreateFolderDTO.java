package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for whenever a new folder is created... or not actually, this might be defunct, anyway, mostly irrelevant
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateFolderDTO {
    public String username;
    public boolean isDirectory;
}