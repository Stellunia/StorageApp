package stellunia.StorageApp.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageUserRepository extends JpaRepository<StorageUser, UUID> {
    Optional<StorageUser> findByUsername(String username);
}
