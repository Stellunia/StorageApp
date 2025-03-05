package stellunia.StorageApp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import stellunia.StorageApp.user.StorageUser;

import java.util.UUID;

// DTO for whenever a user response is required, omits password from printing
@RequiredArgsConstructor
@Getter
public class UserResponseDTO /*extends RepresentationModel<StorageUser>*/ {
    private final UUID id;
    private final String username;
}
