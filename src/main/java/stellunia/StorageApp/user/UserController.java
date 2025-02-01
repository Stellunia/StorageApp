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
@RequestMapping("/storageuser")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUser) {
        try {
            StorageUser storageUser = userService.createUser(createUser.username, createUser.password);
            return ResponseEntity.ok(new UserResponseDTO(storageUser.getId(), storageUser.getUsername()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUser) {
        try {
            String token = userService.login(loginUser.username, loginUser.password);
            return ResponseEntity.ok(token);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserResponseDTO> storageUsers = userService.getAllUsers();
            return ResponseEntity.ok().body(storageUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
