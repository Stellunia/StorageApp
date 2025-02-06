package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for user login sequence
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserDTO {
    public String username;
    public String password;
}
