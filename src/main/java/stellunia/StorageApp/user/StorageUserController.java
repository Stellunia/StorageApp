package stellunia.StorageApp.user;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stellunia.StorageApp.dto.CreateUserDTO;
import stellunia.StorageApp.dto.LoginUserDTO;
import stellunia.StorageApp.dto.UserResponseDTO;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/storageapp/storageuser")
@RequiredArgsConstructor
public class StorageUserController {

    private final StorageUserService storageUserService;

    // Handles the creation of new users
    // Requires body input of username and password
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUser) {
        try {
            StorageUser storageUser = storageUserService.createUser(createUser.username, createUser.password);
            return ResponseEntity.ok(new UserResponseDTO(storageUser.getId(), storageUser.getUsername()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    // Handles the login of existing users
    // requires body input of username and password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUser) {
        try {
            String token = storageUserService.login(loginUser.username, loginUser.password);
            return ResponseEntity.ok(token);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password.");
        }
    }

    // Handles the listing of all existing users
    @GetMapping("/getUsers")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserResponseDTO> storageUsers = storageUserService.getAllUsers();
            return ResponseEntity.ok().body(storageUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
