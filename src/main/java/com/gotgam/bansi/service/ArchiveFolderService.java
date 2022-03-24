package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.ArchiveLinkDTO.ArchiveLinkRequest;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.ArchiveLink;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.ArchiveFolderRepository;
import com.gotgam.bansi.respository.ArchiveLinkRepository;

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
    private final ArchiveLinkRepository linkRepository;
    private final PieceService pieceService;
    private final PieceCollectionService collectionService;
    private final UserService userService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder createFolder(String userId, String name){
        User user = userService.getUserFromId(userId);
        ArchiveFolder folder = new ArchiveFolder(user, name);
        return folderRepository.save(folder);
    }

    public ArchiveFolder getFolder(Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        return folder;
    }

    public List<ArchiveFolder> listFolder(String userId){
        List<ArchiveFolder> folders = folderRepository.findAllByUser_KakaoId(userId);
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
        folder.getCollections().remove(collection);
        return folder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder deletePieceFromFolder(Long pieceId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        folder.getPieces().remove(piece);
        return folder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder addLinkToFolder(ArchiveLinkRequest linkDto, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        ArchiveLink link = new ArchiveLink(linkDto.getUrl().toString(), linkDto.getTime());
        linkRepository.save(link);
        folder.getLinks().add(link);
        return folder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolder deleteLinkFromFolder(Long linkId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        ArchiveLink link = linkRepository.findById(linkId).orElseThrow(() -> new NotFoundException("잘못된 링크 아이디"));
        folder.getLinks().remove(link);
        return folder;
    }

    public ArchiveLink updateLink(Long linkId, ArchiveLinkRequest linkDto){
        ArchiveLink link = linkRepository.findById(linkId).orElseThrow(() -> new NotFoundException("잘못된 링크 아이디"));
        link.setUrl(linkDto.getUrl().toString());
        link.setTime(linkDto.getTime());
        return link;
    }
}
