package stellunia.StorageApp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.dto.UserResponseDTO;
import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.security.JWTService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageUserService implements UserDetailsService {

    private final StorageUserRepository storageUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    // Service for creating a new user
    // Requires a body with the values "username" and "password"
    public StorageUser createUser(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty and/or null.");
        }

        if (username.length() < 2) {
            throw new IllegalArgumentException("Username cannot be less than 2 characters.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty and/or null.");
        }

        if (password.length() < 7) {
            throw new IllegalArgumentException("Password cannot be less than 7 characters.");
        }


        StorageUser user = new StorageUser(username, passwordEncoder.encode(password));
        return storageUserRepository.save(user);
    }

    public StorageUser createOpenIdUser(String username, String oidcId) {
        StorageUser storageUser = new StorageUser(username, null);
        storageUser.setOidcId(oidcId);
        storageUser.setOidcProvider("github");

        return storageUserRepository.save(storageUser);
    }

    // Service for user login
    // Requires a body with the inputs "username" and "password" that match with existing ones within the database
    public String login(String username, String password) {
        StorageUser user = storageUserRepository.findByUsername(username).orElseThrow();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong username and/or password.");
        }

        return jwtService.generateToken(user.getId());
    }

    public Optional<StorageUser> findByOpenId(String id) { return storageUserRepository.findByOidcId(id); }

    // Service for returning a specific user, not used yet
    // Requires "username" input in order to return a value
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return storageUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    // Service for returning all existing users
    public List<UserResponseDTO> getAllUsers(){
        List<StorageUser> users =  storageUserRepository.findAll();
        return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    // Service for getting folder by a specific name
    public StorageUser getUserByName(String username) {
        return storageUserRepository.
                findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }
}
