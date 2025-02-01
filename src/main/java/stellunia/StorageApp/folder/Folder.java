/*package stellunia.StorageApp.folder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stellunia.StorageApp.storageUser.User;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

    @Id
    private UUID folderId;

    private String username;
    private boolean isDirectory = true; // Maybe make the directory a different object? Kind of like blogpost vs comments?
    // Users exists, users make blogposts (folders), users can then comment on these blogposts (upload files)
    // - but restrict the accessibility of the blogposts to just be accessible to one person
    private long size;
    private Instant lastModified;

    @ManyToOne
    private User storageUser;

    @OneToMany(mappedBy = "folders")
    private List<Files> files;

    public Folder(String username, long size, boolean isDirectory, User storageUser) {
        this.folderId = UUID.randomUUID();
        this.username = username;
        this.storageUser = storageUser;
        this.size = size;
        this.isDirectory = isDirectory;
        this.files = new ArrayList<>();
    }
}*/
