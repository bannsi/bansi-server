package com.gotgam.bansi.DTO.ArchiveFolderDTO;

import java.util.Set;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.DTO.CollectionDTO.PieceCollectionDTO;
import com.gotgam.bansi.model.ArchiveFolder;
import com.gotgam.bansi.model.ArchiveLink;
import com.gotgam.bansi.model.ThumbNail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ArchiveFolderDTO {
    private Long id;
    private String name;
    private Set<ThumbNailDTO> thumbNails;
    private Set<PieceCollectionDTO> collections;
    private Set<ArchiveLink> links;

    public ArchiveFolderDTO(Long id, String name, Set<ThumbNail> thumbNails, Set<PieceCollectionDTO> collections, Set<ArchiveLink> links){
        this.id = id;
        this.name = name;
        this.thumbNails = thumbNails.stream().map(tn -> new ThumbNailDTO(tn.getPiece().getPieceId(), tn.getUser().getKakaoId(), tn.getEncoded())).collect(Collectors.toSet());
        this.collections = collections;
        this.links = links;
    }
    
    public ArchiveFolderDTO(ArchiveFolder folder){
        this.id = folder.getId();
        this.name = folder.getName();
        this.thumbNails = folder.getThumbNails().stream().map(tn -> new ThumbNailDTO(tn)).collect(Collectors.toSet());
        this.collections = folder.getCollections().stream().map(collection -> new PieceCollectionDTO(collection)).collect(Collectors.toSet());
        this.links = folder.getLinks();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ArchiveFolderRequest{
        private String name;
    }
}
