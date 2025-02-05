/*
package stellunia.StorageApp.backups;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
//import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.user.StorageUser;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class StorageFileSilly {

    // TODO: Create a database alongside tables if they don't exists so that the application can establish itself

    @Id
    private UUID fileId;

    private String fileName;
    private String content;
    private long fileSize;
    //private boolean isDirectory = false; // Maybe make the directory a different object? Kind of like blogpost vs comments?
    // Users exists, users make blogposts (folders), users can then comment on these blogposts (upload files)
    // - but restrict the accessibility of the blogposts to just be accessible to one person
*/
/*    private long size;*//*


    @ManyToOne
    private StorageUser storageUser;

*/
/*    @ManyToOne
    private StorageFolder folder;*//*


    public StorageFileSilly(StorageUser user, String fileName, String content, long fileSize*/
/*,
    long size, boolean isDirectory, StorageFolder folder*//*
) {
        this.fileId = UUID.randomUUID();
        this.fileName = fileName;
        this.storageUser = user;
        this.content = content;
        this.fileSize = fileSize;
        //this.size = size;
        //this.isDirectory = isDirectory;
*/
/*        this.folder = folder;*//*

    }
}
*/
