package com.gotgam.bansi.DTO.ArchiveFolderDTO;

import java.util.Set;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.ArchiveLink;
import com.gotgam.bansi.model.PieceCollection;
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
    private Set<PieceCollection> collections;
    private Set<ArchiveLink> links;

    public ArchiveFolderDTO(Long id, String name, Set<ThumbNail> thumbNails, Set<PieceCollection> collections, Set<ArchiveLink> links){
        this.id = id;
        this.name = name;
        this.thumbNails = thumbNails.stream().map(tn -> new ThumbNailDTO(tn.getPiece().getPieceId(), tn.getUser().getKakaoId(), tn.getEncoded())).collect(Collectors.toSet());
        this.collections = collections;
        this.links = links;
    }
    
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ArchiveFolderRequest{
        private String name;
    }
}
