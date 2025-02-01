package stellunia.StorageApp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UserResponseDTO {
    private final UUID id;
    private final String username;
}
