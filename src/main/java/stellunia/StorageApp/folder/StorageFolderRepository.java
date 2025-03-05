
package stellunia.StorageApp.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageFolderRepository extends JpaRepository<StorageFolder, UUID> {

    //@Query("SELECT * FROM storage_folder WHERE folder_name = ?;")
    // findByFolderName my beloathed, you are fantastic but good gosh you're annoying
    Optional<StorageFolder> findByFolderName(String name);
    List<StorageFolder> findByStorageUserId(UUID userId);

}
