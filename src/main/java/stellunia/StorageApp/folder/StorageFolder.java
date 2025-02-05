package stellunia.StorageApp.folder;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import stellunia.StorageApp.file.StorageFile;
import stellunia.StorageApp.user.StorageUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "storage_folder")
public class StorageFolder {

    @Id
    @Column(name = "folder_id")
    private UUID folderId;

    @Column(name = "folder_name")
    private String folderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_user_id")
    private StorageUser storageUser;

    @ManyToOne
    private StorageFolder parentFolder;

    @OneToMany(mappedBy = "storageFolder")
    private List<StorageFile> storageFiles;

    public StorageFolder() {this.folderId = UUID.randomUUID();}

    @Autowired
    public StorageFolder(String folderName/*, StorageUser storageUser, StorageFolder parentFolder*/ ) {
        this.folderId = UUID.randomUUID();
        this.folderName = folderName;
        this.storageFiles = new ArrayList<>();
        //this.storageUser = storageUser;
        //this.parentFolder = parentFolder;
    }
}
