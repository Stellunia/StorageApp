package stellunia.StorageApp.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageFileRepository extends JpaRepository<StorageFile, UUID> {
    Optional<StorageFile> findByFileName(String name);
    /*List<StorageFileSilly> findByUserId(UUID userId);
    Optional<StorageFileSilly> findByFileNameAndUserId(String filename, UUID userId);*/
}
