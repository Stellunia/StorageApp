package stellunia.StorageApp.fileDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stellunia.StorageApp.file.StorageFileNotFoundException;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class StorageFileController {

    @Autowired
    private StorageFileService storageFileService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponseDTO> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageFileNotFoundException("Cannot upload empty file.");
            }
            StorageFile storageFile = storageFileService.store(multipartFile);

            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")
                    .path(storageFile.getFileId().toString())
                    .toUriString();

            return ResponseEntity.ok(new FileUploadResponseDTO(
                    storageFile.getFileName(),
                    downloadUrl,
                    multipartFile.getContentType(),
                    multipartFile.getSize()
            ));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        Optional<StorageFile> fileOptional = storageFileService.getFile(id);

        if (fileOptional.isPresent()) {
            StorageFile storageFile = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storageFile.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, storageFile.getFileType())
                    .body(storageFile.getFileData());
        }

        return ResponseEntity.notFound().build();
    }
}
