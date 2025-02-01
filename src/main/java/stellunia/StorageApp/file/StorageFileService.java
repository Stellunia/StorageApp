package stellunia.StorageApp.file;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
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

    public Collection<StorageFile> getAllFiles(int page) {
        return fileRepository.findAll(PageRequest.of(page, 20)).toList();
    }
}
