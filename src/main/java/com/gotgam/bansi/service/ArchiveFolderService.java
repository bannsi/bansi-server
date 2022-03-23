package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.ArchiveFolderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArchiveFolderService {
    private final ArchiveFolderRepository folderRepository;
    private final UserService userService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder createFolder(String userId, String name){
        User user = userService.getUserFromId(userId);
        ArchiveFolder folder = new ArchiveFolder(user, name);
        return folderRepository.save(folder);
    }

    public List<ArchiveFolder> listFolder(String userId){
        List<ArchiveFolder> folders = folderRepository.findAllByUser_Id(userId);
        return folders;
    }

    public ArchiveFolder updateFolder(Long folderId, String name){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.setName(name);
        return folderRepository.save(folder);
    }

    public void deleteFolder(Long folderId){
        folderRepository.deleteById(folderId);
    }
}
