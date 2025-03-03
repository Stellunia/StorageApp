package stellunia.StorageApp.user;

import lombok.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import stellunia.StorageApp.dto.CreateUserDTO;
import stellunia.StorageApp.dto.LoginUserDTO;
import stellunia.StorageApp.dto.UserResponseDTO;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/storageapp/storageuser")
@RequiredArgsConstructor
public class StorageUserController {

    private final StorageUserService storageUserService;

    // Handles the creation of new users
    // Requires body input of username and password
/*    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUser) {
        try {
            StorageUser storageUser = storageUserService.createUser(createUser.username, createUser.password);
            return ResponseEntity.ok(new UserResponseDTO(storageUser.getId(), storageUser.getUsername()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }*/

    @GetMapping("/login")
    public Map<String, Object> StorageUser(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("username", principal.getAttribute("username"));
    }

    // Handles the login of existing users
    // requires body input of username and password
/*    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUser) {
        try {
            String token = storageUserService.login(loginUser.username, loginUser.password);
            return ResponseEntity.ok(token);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password.");
        }
    }*/

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

    @GetMapping("/helloWorld")
    public ResponseEntity<?> helloWorld(){
        try {
            System.out.println("Hello World!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
