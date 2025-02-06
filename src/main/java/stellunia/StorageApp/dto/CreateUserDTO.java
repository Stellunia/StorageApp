package stellunia.StorageApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for new user creation
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDTO {
    public String username;
    public String password;
}
