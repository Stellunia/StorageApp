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

    public Optional<StorageFile> getFile(UUID id) {
        return storageFileRepository.findById(id);
    }

    public List<StorageFile> getAllFiles() {
        return storageFileRepository.findAll();
    }

    public void deleteFile(UUID id){
        StorageFile storageFile = storageFileRepository.findById(id).orElseThrow(() ->new RuntimeException("File not found."));
        storageFileRepository.delete(storageFile);
    }
}
