package stellunia.StorageApp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.dto.UserResponseDTO;
import stellunia.StorageApp.security.JWTService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageUserService implements UserDetailsService {

    private final StorageUserRepository storageUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

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


        StorageUser user = new StorageUser(username, password);
        return storageUserRepository.save(user);
    }

    public String login(String username, String password) {
        StorageUser user = storageUserRepository.findByUsername(username).orElseThrow();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong username and/or password.");
        }

        return jwtService.generateToken(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return storageUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<UserResponseDTO> getAllUsers(){
        List<StorageUser> users =  storageUserRepository.findAll();
        return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }
}
