package stellunia.StorageApp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

// DTO for whenever a user response is required, omits password from printing
@RequiredArgsConstructor
@Getter
public class UserResponseDTO {
    private final UUID id;
    private final String username;
}
