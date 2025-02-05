
package stellunia.StorageApp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.user.StorageUserRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StorageFolderService {

    @Autowired
    private final StorageFolderRepository storageFolderRepository;
    //private final StorageUserRepository storageUserRepository;

    public StorageFolder createFolder(String folderName/*, StorageUser storageUser,
                                      StorageFolder childFolder, StorageFolder parentFolder*/) {
        // User storageUser = userRepository.findById(userId)
        //                    .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Optional<StorageFolder> storageFolderExists = storageFolderRepository.findByFolderName(folderName);
        if (storageFolderExists.isPresent()) {
            throw new IllegalArgumentException("Folder already exists.");
        }

        StorageFolder storageFolder = new StorageFolder(folderName);
        return storageFolderRepository.save(storageFolder);
    }

    public StorageFolder updateFolder(StorageFolder folderName) {
        StorageFolder storageFolder = storageFolderRepository.
                findByFolderName(folderName.getFolderName())
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));
                storageFolderRepository.save(storageFolder);
                // Need more?
        return storageFolder;
    }

    // @Name returns the folder of the given name
    public StorageFolder getFolderByName(String folderName) {
        return storageFolderRepository.
                findByFolderName(folderName)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found."));
    }

    // Returns all folders within the database
    public List<StorageFolder> getAllFolders() {
        return storageFolderRepository.findAll();
    }

}

