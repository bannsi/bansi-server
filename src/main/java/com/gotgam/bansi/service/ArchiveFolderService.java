package com.gotgam.bansi.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ArchiveLinkDTO.ArchiveLinkRequest;
import com.gotgam.bansi.DTO.ArchiveFolderDTO.ArchiveFolderDTO;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.ArchiveLink;
import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.model.ThumbNail;
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
    private final ThumbNailService thumbNailService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO createFolder(String userId, String name){
        User user = userService.getUserFromId(userId);
        ArchiveFolder folder = new ArchiveFolder(user, name);
        folderRepository.save(folder);
        return new ArchiveFolderDTO(folder);
    }

    public ArchiveFolderDTO getFolder(Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        return new ArchiveFolderDTO(folder);
    }

    public List<ArchiveFolderDTO> listFolder(String userId){
        List<ArchiveFolder> folders = folderRepository.findAllByUser_KakaoId(userId);
        return folders.stream().map(folder -> new ArchiveFolderDTO(folder)).collect(Collectors.toList());
    }

    public ArchiveFolderDTO updateFolder(Long folderId, String name){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.setName(name);
        return new ArchiveFolderDTO(folderRepository.save(folder));
    }

    public void deleteFolder(Long folderId){
        folderRepository.deleteById(folderId);
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO addPieceToFolder(Long pieceId, Long folderId){
        ThumbNail thumbNail = thumbNailService.getByPiece_Id(pieceId);
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.getThumbNails().add(thumbNail);
        return new ArchiveFolderDTO(folderRepository.save(folder));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO addCollectionToFolder(Long collectionId, Long folderId){
        PieceCollection collection = collectionService.getCollection(collectionId);
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.getCollections().add(collection);
        return new ArchiveFolderDTO(folderRepository.save(folder));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO deleteCollectionFromFolder(Long collectionId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        PieceCollection collection = collectionService.getCollection(collectionId);
        folder.getCollections().remove(collection);
        return new ArchiveFolderDTO(folder);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO deletePieceFromFolder(Long pieceId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        folder.getThumbNails().remove(thumbNailService.getByPiece_Id(pieceId));
        return new ArchiveFolderDTO(folder);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO addLinkToFolder(ArchiveLinkRequest linkDto, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        ArchiveLink link = new ArchiveLink(linkDto.getUrl().toString(), linkDto.getTime());
        linkRepository.save(link);
        folder.getLinks().add(link);
        return new ArchiveFolderDTO(folder);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArchiveFolderDTO deleteLinkFromFolder(Long linkId, Long folderId){
        ArchiveFolder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("잘못된 아카이브 폴더 아이디"));
        ArchiveLink link = linkRepository.findById(linkId).orElseThrow(() -> new NotFoundException("잘못된 링크 아이디"));
        folder.getLinks().remove(link);
        return new ArchiveFolderDTO(folder);
    }

    public ArchiveLink updateLink(Long linkId, ArchiveLinkRequest linkDto){
        ArchiveLink link = linkRepository.findById(linkId).orElseThrow(() -> new NotFoundException("잘못된 링크 아이디"));
        link.setUrl(linkDto.getUrl().toString());
        link.setTime(linkDto.getTime());
        return link;
    }
}
