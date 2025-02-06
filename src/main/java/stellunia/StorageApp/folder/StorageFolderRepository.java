
package stellunia.StorageApp.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stellunia.StorageApp.file.StorageFile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageFolderRepository extends JpaRepository<StorageFolder, UUID> {

    //@Query("SELECT * FROM storage_folder WHERE folder_name = ?;")
    // findByFolderName my beloathed, you are fantastic but good gosh you're annoying
    Optional<StorageFolder> findByFolderName(String name);

}
