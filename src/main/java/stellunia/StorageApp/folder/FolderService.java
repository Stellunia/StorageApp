/*
package stellunia.StorageApp.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.storageUser.User;
import stellunia.StorageApp.storageUser.UserRepository;

import java.util.ArrayList;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public Folder createFolder(String username, long size, boolean isDirectory, User storageUser) {
        //User storageUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));

        Folder folder = new Folder(username, size, isDirectory, storageUser);
        return folderRepository.save(folder);
    }

    public Collection<Folder> getAllFolders(int page) {
        return folderRepository.findAll(PageRequest.of(page, 5)).toList();
    }

}
*/
