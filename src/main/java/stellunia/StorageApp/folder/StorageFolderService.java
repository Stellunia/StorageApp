
package stellunia.StorageApp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.StorageUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class StorageFolderService {

    @Autowired
    private final StorageFolderRepository storageFolderRepository;
    private final StorageUserRepository storageUserRepository;

    // Service for handling the creation of folders
    // Handles the creation of a folder, alongside matching it to already existing ones if possible
    public StorageFolder createFolder(String folderName, String storageUser/*,
                                      StorageFolder childFolder, StorageFolder parentFolder*/) {
        // User storageUser = userRepository.findById(userId)
        //                    .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Optional<StorageUser> storageUserExists = storageUserRepository.findByUsername(storageUser);
        if (!storageUserExists.isPresent())
        {
            throw new IllegalArgumentException("User does not exist.");
        } else if (storageUserExists.isEmpty())
        {
            throw new IllegalArgumentException("User field is empty.");
        }

        Optional<StorageFolder> storageFolderExists = storageFolderRepository.findByFolderName(folderName);
        if (storageFolderExists.isPresent()) {
            throw new IllegalArgumentException("Folder already exists.");
        }

        StorageFolder storageFolder = new StorageFolder(folderName, storageUserExists.get()); //Need to fetch the storage user called in the method
        return storageFolderRepository.save(storageFolder);
    }

    // Service for updating folder structure
    // Handles fetching of a folder by name and then saving the folder
    public StorageFolder updateFolder(StorageFolder folderName) {
        StorageFolder storageFolder = storageFolderRepository.
                findByFolderName(folderName.getFolderName())
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));
                storageFolderRepository.save(storageFolder);
                // Need more? - Nopesies
        return storageFolder;
    }

    // Service for getting folder by a specific name
    public StorageFolder getFolderByName(String folderName) {
        return storageFolderRepository.
                findByFolderName(folderName)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found."));
    }

    public List<StorageFolder> getUserFolders(String userId) {
        return storageFolderRepository.findByStorageUserId(UUID.fromString(userId));
/*        UUID findByStorageUserId;
        storageUserRepository.findById(UUID.fromString(userId));
        return storageFolderRepository
                .findByStorageUserId(UUID.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found."));*/
    }

    // Service for returning all folders within the database
    public List<StorageFolder> getAllFolders() {
        return storageFolderRepository.findAll();
    }

}

