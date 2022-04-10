package com.gotgam.bansi.DTO.CollectionDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ItemDTO.ItemDTO;
import com.gotgam.bansi.model.PieceCollection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceCollectionDTO {
    private Long id;
    private String coverImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private List<ItemDTO> items;

    public PieceCollectionDTO(PieceCollection collection){
        this.id = collection.getCollectionId();
        this.coverImage = collection.getCoverImage();
        this.startDate = collection.getStartDate();
        this.endDate = collection.getEndDate();
        this.place = collection.getPlace().getName();
        this.items = collection.getItems().stream().map(item -> new ItemDTO(item)).collect(Collectors.toList());
    }
}
