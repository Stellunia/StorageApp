package stellunia.StorageApp.user;

import lombok.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import stellunia.StorageApp.dto.CreateUserDTO;
import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.dto.LoginUserDTO;
import stellunia.StorageApp.dto.UserResponseDTO;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.file.StorageFileController;
import stellunia.StorageApp.file.StorageFileService;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/storageapp/storageuser")
@RequiredArgsConstructor
public class StorageUserController {

    private final StorageUserService storageUserService;
    private final StorageFileService storageFileService;


    // Controller for outputting the user's "homepage" by listing all files and the relevant links that the user can take with them
    @GetMapping
    public ResponseEntity<?> userHomePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauth2Token.getPrincipal();

        String oidcId = oAuth2User.getAttribute("id")+ "";
        String username = oAuth2User.getAttribute("login");
        UUID userId = storageUserService.getUserByName(username).getId();
        System.out.println("Welcome, " + username);


        List<FileResponseDTO> storageFiles = storageFileService.getUserFiles(String.valueOf(userId)).stream()
                .map(FileResponseDTO::fromModel)
                .toList();

        for (final FileResponseDTO storageFile : storageFiles) {
            Optional<StorageFile> fileOptional = storageFileService.getFileById(UUID.fromString(storageFile.getFileId()));
            // Fetch this a different way 'cause it's not cooperating at all - skips the "if fileOptional.isPresent" call and just fucks right off
            if (fileOptional.isPresent()) { // ^ -> storageFiles -> FileResponseDTO -> fileId
                UUID fileId = fileOptional.get().getFileId();
                //Link selfLink = linkTo(methodOn(StorageFileController.class).getUserFiles(String.valueOf(userId))).withSelfRel();

                Link downloadLink = linkTo(methodOn(StorageFileController.class).downloadFile(fileId)).withRel("download");
                Link deleteLink = linkTo(methodOn(StorageFileController.class).deleteFile(fileId)).withRel("delete");
                storageFile.add(downloadLink);
                storageFile.add(deleteLink);
            }
            //String fileId = storageFileService.getFileByName(storageFile.getFileId()).toString();
        }


        Link link = linkTo(methodOn(StorageFileController.class).getUserFiles(String.valueOf(userId))).withSelfRel();
        CollectionModel<FileResponseDTO> result = CollectionModel.of(storageFiles, link);
        ResponseEntity.ok("Welcome, " + username + "\n");
        return ResponseEntity.ok(result);
    }

    // Handles the creation of new users
    // Requires body input of username and password
    // Redundant as its own function, but leaving it here cause I don't want to create a mess at this point
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUser) {
        try {
            StorageUser storageUser = storageUserService.createUser(createUser.username, createUser.password);
            return ResponseEntity.ok(new UserResponseDTO(storageUser.getId(), storageUser.getUsername()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    // This is redundant at this point, meant to handle login of users but honestly, beats me if it does anything at all
    @GetMapping("/login")
    public Map<String, Object> StorageUser(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("username", principal.getAttribute("username"));
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

    // Handles the listing of all existing users - gaslight, gatekeep, girlboss... or admin, in more proper terms
    @GetMapping("/admin/getUsers")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserResponseDTO> storageUsers = storageUserService.getAllUsers();
            return ResponseEntity.ok().body(storageUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Test method for outputting various items necessary to figure out what the heck is what - not meant to be a function of the application
    @GetMapping("/helloWorld")
    public ResponseEntity<?> helloWorld(){
        try {
            System.out.println("Hello World!"); // Change this later on, cause it a bit chaos - need to be scruncled
            System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = oauth2Token.getPrincipal();

            String oidcId = oAuth2User.getName();
            String username = oAuth2User.getAttribute("login");
            System.out.println(oidcId + " | " + username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
