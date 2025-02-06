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

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class StorageFileService {

    @Autowired
    private final StorageFileRepository storageFileRepository;
    private final StorageFolderRepository storageFolderRepository;
    private final StorageFolderService storageFolderService;

    // Service for the file upload sequence
    // Handles input of the file itself as well as attributing it to an existing folder.
    @Transactional
    public StorageFile uploadFile(MultipartFile multipartFile, StorageFolder storageFolder) throws IOException {

        Optional<StorageFile> storageFileExists = storageFileRepository.findByFileName(multipartFile.getOriginalFilename());
        if (storageFileExists.isPresent()) {
            throw new IllegalArgumentException("File already exists.");
        }

        Optional<StorageFolder> storageFolderExists = storageFolderRepository.findByFolderName(storageFolder.getFolderName());
        if (storageFolderExists.isEmpty()) {
            throw new NoSuchElementException("Folder doesn't exist.");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        StorageFile storageFile = new StorageFile(fileName, multipartFile.getContentType(),
                    multipartFile.getBytes(), storageFolderExists.get());

        storageFileRepository.save(storageFile);
        storageFolderExists.get().getStorageFiles().add(storageFile);
        storageFolderService.updateFolder(storageFolderExists.get());

        return storageFile;
    }

    // Service for getting a specific file
    public Optional<StorageFile> getFile(UUID id) {
        return storageFileRepository.findById(id);
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
