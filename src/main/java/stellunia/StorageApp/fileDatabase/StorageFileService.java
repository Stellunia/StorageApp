package stellunia.StorageApp.fileDatabase;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import stellunia.StorageApp.file.FileRepository;
import stellunia.StorageApp.fileDatabase.StorageFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StorageFileService {

    @Autowired
    private final FileRepository fileRepository;

    @Transactional
    public StorageFile store(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        StorageFile storageFile = new StorageFile(fileName, multipartFile.getContentType(), multipartFile.getBytes());

        return fileRepository.save(storageFile);
    }

    public Optional<StorageFile> getFile(UUID id) {
        return fileRepository.findById(id);
    }
}
