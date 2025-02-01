/*package stellunia.StorageApp.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
*//*import stellunia.StorageApp.folder.Folder;
import stellunia.StorageApp.folder.FolderRepository;*//*
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    //private final FolderRepository folderRepository;

    public StorageFileSilly uploadFileToFolder(UUID userId, String name, String content, long fileSize) {
        StorageUser user = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

*//*        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found."));*//*

        StorageFileSilly storageFile = new StorageFileSilly(user, name, content, fileSize);
        return fileRepository.save(storageFile);
    }

*//*    public StorageFileSilly uploadFileToFolder(UUID userId, String name, String content, long fileSize) {
        StorageUser user = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

*//**//*        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found."));*//**//*

        StorageFileSilly storageFile = new StorageFileSilly(user, name, content, fileSize);
        return fileRepository.save(storageFile);
    }*//*
}*/
