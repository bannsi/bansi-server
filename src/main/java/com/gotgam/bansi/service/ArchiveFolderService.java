package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceCollection;
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
    private final PieceService pieceService;
    private final PieceCollectionService collectionService;
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
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder addPieceToFolder(Long pieceId, Long folderId){
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.getPieces().add(piece);
        return folderRepository.save(folder);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder addCollectionToFolder(Long collectionId, Long folderId){
        PieceCollection collection = collectionService.getCollection(collectionId);
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.getCollections().add(collection);
        return folderRepository.save(folder);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder deleteCollectionFromFolder(Long collectionId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        PieceCollection collection = collectionService.getCollection(collectionId);
        // TEST
        folder.getCollections().remove(collection);
        return folder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder deletePieceFromFolder(Long pieceId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        // TEST
        folder.getPieces().remove(piece);
        return folder;
    }
}
