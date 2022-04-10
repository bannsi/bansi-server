package com.gotgam.bansi.DTO.CollectionDTO;

import java.util.List;

import com.gotgam.bansi.DTO.ResponseDTO;

import lombok.Getter;

@Getter
public class ListPieceCollectionResponse extends ResponseDTO{
    private List<PieceCollectionDTO> body;
        public ListPieceCollectionResponse(String code, String message, List<PieceCollectionDTO> pieceCollections){
            super(code, message);
            this.body = pieceCollections;
        }    
}
