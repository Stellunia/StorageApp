/*
package stellunia.StorageApp.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import stellunia.StorageApp.folder.StorageFolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import stellunia.StorageApp.dto.CreateFileDTO;
//import stellunia.StorageApp.dto.FileResponseDTO;
import stellunia.StorageApp.utility.ErrorResponseDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileController {

    private final StorageService fileService;

    @Autowired
    public FileController(StorageService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String listUploadedFiles(Model model) throws IOException{
        model.addAttribute("files", fileService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class, "serveFile",
                        path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
        return "uploadForm";
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploadDefunct")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) {

        fileService.uploadFile(multipartFile);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + multipartFile.getOriginalFilename() + "!");

        return ResponseEntity.ok("stinky upload done.");
    }

    @GetMapping(*/
/*value = *//*
"/downloadDefunct"*/
/*, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*//*
)
    @ResponseBody
    public ResponseEntity<?> downloadFile(@RequestParam("file_name") String fileName,
                                          RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message",
                "You successfully downloaded " + fileName + ".");
        Resource file = fileService.loadAsResource(fileName);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

        //fileService.load(fileName);
        //return ResponseEntity.ok("stinky download works and might be done.");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
        return new FileSystemResource(fileService.load(fileName));
    }

*/
/*    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @ModelAttribute CreateFileDTO createFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            long fileSize = multipartFile.getSize();
            String fileContent = new String(multipartFile.getBytes());
            StorageFileSilly storageFile = fileService.uploadFileToFolder(createFile.userId, fileName, *//*
*/
/*createFile.folderName,*//*
*/
/* fileContent, fileSize);
            return ResponseEntity.ok(FileResponseDTO.fromModel(storageFile));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }*//*


*/
/*    public File(String username, User storageUser, long size, boolean isDirectory, StorageFolder folder) {
        this.fileId = UUID.randomUUID();
        this.username = username;
        this.storageUser = storageUser;
        this.size = size;
        this.isDirectory = isDirectory;
        this.folder = folder;
    }*//*


}
*/
