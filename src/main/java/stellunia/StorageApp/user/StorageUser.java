package stellunia.StorageApp.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.folder.StorageFolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "storageUser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StorageUser implements UserDetails{

    @Id
    private UUID id;

    private String oidcId = null;
    private String oidcProvider = null;

    private String username;
    private String password;
    private boolean admin = false;

    @OneToMany(mappedBy = "storageUser", fetch = FetchType.LAZY)
    private List<StorageFile> storageUserFiles = new ArrayList<>();

    @OneToMany(mappedBy = "storageUser", fetch = FetchType.EAGER)
    private List<StorageFolder> storageUserFolders = new ArrayList<>();

    public StorageUser(String name, String password) {
        this.id = UUID.randomUUID();
        this.username = name;
        this.password = password;
        //this.userFiles = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + "\'" +
                ", password='" + password + "\'" +
                "}";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (admin) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return List.of();
        }
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return this.username; }
}
