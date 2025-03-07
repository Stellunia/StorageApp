package stellunia.StorageApp.file;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import stellunia.StorageApp.folder.StorageFolder;
import stellunia.StorageApp.folder.StorageFolderRepository;
import stellunia.StorageApp.folder.StorageFolderService;
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.StorageUserRepository;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class StorageFileService {

    @Autowired
    private final StorageFileRepository storageFileRepository;
    private final StorageFolderRepository storageFolderRepository;
    private final StorageUserRepository storageUserRepository;
    private final StorageFolderService storageFolderService;

    // Service for the file upload sequence
    // Handles input of the file itself as well as attributing it to an existing folder.
    @Transactional
    public StorageFile uploadFile(MultipartFile multipartFile, StorageFolder storageFolder, String storageUser) throws IOException {

        Optional<StorageFile> storageFileExists = storageFileRepository.findByFileName(multipartFile.getOriginalFilename());
        if (storageFileExists.isPresent()) {
            throw new IllegalArgumentException("File already exists.");
        }

        Optional<StorageFolder> storageFolderExists = storageFolderRepository.findByFolderName(storageFolder.getFolderName());
        if (storageFolderExists.isEmpty()) {
            throw new NoSuchElementException("Folder doesn't exist.");
        }

        Optional<StorageUser> storageUserExists = storageUserRepository.findByUsername(storageUser);
        if (storageUserExists.isEmpty()) {
            throw new NoSuchElementException("User does not exist.");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        StorageFile storageFile = new StorageFile(fileName, multipartFile.getContentType(),
                    multipartFile.getBytes(), storageFolderExists.get(), storageUserExists.get());

        storageFileRepository.save(storageFile);
        storageFolderExists.get().getStorageFiles().add(storageFile);
        storageFolderService.updateFolder(storageFolderExists.get());

        return storageFile;
    }

    // Service for getting a specific file
    public Optional<StorageFile> getFileById(UUID id) {
        return storageFileRepository.findById(id);
    }

    // Meant to cut out the necessities of needing UUID, a replacement for getFileById - but have not implemented it - works the same
    public Optional<StorageFile> getFileByStringId(String id) {
        return storageFileRepository.findById(UUID.fromString(id));
    }

    // Service for getting a specific file by its name
    public Optional<StorageFile> getFileByName(String fileName) {
        return storageFileRepository.findByFileName(fileName);
    }

/*    public Optional<StorageFile> getFileByUserId(UUID fileId, UUID userId) {
        storageFileRepository.findByFileId(fileId);
        return storageFileRepository.findByStorageUserId(userId);
    }*/

    // Currently the main way to get files for the user - service for checking any files attributed to the user logged in
    public List<StorageFile> getUserFiles(String userId) {
        return storageFileRepository.findByStorageUserId(UUID.fromString(userId));
    }

    // Service for getting all files
    public List<StorageFile> getAllFiles() {
        return storageFileRepository.findAll();
    }

    // Service for deleting files
    public void deleteFile(UUID id){
        StorageFile storageFile = storageFileRepository.findById(id).orElseThrow(() ->new RuntimeException("File not found."));
        storageFileRepository.delete(storageFile);
    }
}
