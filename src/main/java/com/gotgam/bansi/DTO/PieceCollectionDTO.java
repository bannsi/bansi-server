package com.gotgam.bansi.DTO;

import java.util.Date;
import java.util.List;

import com.gotgam.bansi.model.Item;
import com.gotgam.bansi.model.PieceCollection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PieceCollectionDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PieceCollectionRequest {
        private String title;
        private String coverImage;
        private List<Item> items;
        @DateTimeFormat(iso = ISO.DATE)
        private Date startDate;
        @DateTimeFormat(iso = ISO.DATE)
        private Date endDate;
    }   
    @Getter
    public static class PieceCollectionResponse extends ResponseDTO {
        private PieceCollection body;
        public PieceCollectionResponse(String code, String message, PieceCollection pieceCollection){
            super(code, message);
            this.body = pieceCollection;
        }
    }

    @Getter
    public static class ListPieceCollectionResponse extends ResponseDTO {
        private List<PieceCollection> body;
        public ListPieceCollectionResponse(String code, String message, List<PieceCollection> pieceCollections){
            super(code, message);
            this.body = pieceCollections;
        }
    }
}
