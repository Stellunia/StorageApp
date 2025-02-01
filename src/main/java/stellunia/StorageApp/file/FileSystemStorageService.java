package stellunia.StorageApp.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        if(properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location cannot be empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path parentDirectory = this.rootLocation.resolve(
                    Paths.get(multipartFile.getOriginalFilename()).normalize().toAbsolutePath()).getParent();

            Path destinationFile = this.rootLocation.resolve(parentDirectory + "\\static\\" + multipartFile.getOriginalFilename());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files. ", e);
        }
    }

    @Override
    public Path load(String filename) {

        if (filename.isEmpty()) {
            throw new StorageException("Failed to load: " + filename);
        }
        Path parentDirectory = this.rootLocation.resolve(
                Paths.get(filename).normalize().toAbsolutePath()).getParent();

        return rootLocation.resolve(parentDirectory + "\\static\\" + filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("storage farted out all things.");
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Storage farted and couldn't initialize after scaring itself.", e);
        }
    }
}
