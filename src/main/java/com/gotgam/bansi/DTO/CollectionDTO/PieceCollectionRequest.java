package com.gotgam.bansi.DTO.CollectionDTO;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.gotgam.bansi.DTO.ItemDTO.ItemRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceCollectionRequest {
    private String title;
        private String coverImage;
        private List<ItemRequest> items;
        private LocalDate startDate;
        private LocalDate endDate;
        @NotBlank
        private String place;   
}
