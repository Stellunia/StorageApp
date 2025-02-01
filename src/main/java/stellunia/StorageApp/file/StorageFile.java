package stellunia.StorageApp.file;

import jakarta.persistence.*;
import lombok.Data;
import stellunia.StorageApp.user.StorageUser;

import java.util.UUID;

@Entity
@Table(name = "storage_file")
@Data
public class StorageFile {

    @Id
    @Column(name = "file_id")
    private UUID fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_user_id")
    private StorageUser storageUser;

/*    @ManyToOne
    private Folder folder;*/

    public StorageFile() {
        this.fileId = UUID.randomUUID();
    }

    public StorageFile(String fileName, String fileType, byte[] fileData/*,
    long size, boolean isDirectory, Folder folder*/) {
        this.fileId = UUID.randomUUID();
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
        //this.size = size;
        //this.isDirectory = isDirectory;
        /*        this.folder = folder;*/
    }
}

