package stellunia.StorageApp.file;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.user.StorageUser;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "storage_file")
@Data
public class StorageFile extends RepresentationModel<StorageFile> {

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

    @ManyToOne
    @JoinColumn(name = "storage_folder_id")
    private StorageFolder storageFolder;

    public StorageFile() {this.fileId = UUID.randomUUID();}

    @Autowired
    public StorageFile(String fileName, String fileType, byte[] fileData, StorageFolder storageFolder, StorageUser storageUser) {
        this.fileId = UUID.randomUUID();
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
        this.storageFolder = storageFolder;
        this.storageUser = storageUser;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

