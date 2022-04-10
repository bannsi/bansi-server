package com.gotgam.bansi.DTO.CollectionDTO;

import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.PieceCollection;

import lombok.Getter;

@Getter
public class PieceCollectionResponse extends ResponseDTO {
    private PieceCollection body;
        public PieceCollectionResponse(String code, String message, PieceCollection pieceCollection){
            super(code, message);
            this.body = pieceCollection;
        }
}
