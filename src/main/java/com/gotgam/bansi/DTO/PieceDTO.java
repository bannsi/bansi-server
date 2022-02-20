package com.gotgam.bansi.DTO;

import java.util.Date;
import java.util.List;

import com.gotgam.bansi.model.Piece;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class PieceDTO {
    @Getter
    @Setter
    public static class PieceRequest {
        private String title;
        @DateTimeFormat(iso = ISO.DATE)
        private Date date;
        private String content;
        private Double latitude;
        private Double longitude;
        private String address;
        private String addressDetail;
        private String placeUrl;
        private List<MultipartFile> images;
        private List<Long> keywords;
        private List<Long> optionalKeywords;
        private List<Long> whos;    
    }    

    @Getter
    public static class PieceResponse extends ResponseDTO {
        private Piece body;
        public PieceResponse(String code, String message, Piece piece){
            super(code, message);
            this.body = piece;
        }
    }

    @Getter
    public static class ListPieceResponse extends ResponseDTO {
        private List<Piece> body;
        public ListPieceResponse(String code, String message, List<Piece> pieces){
            super(code, message);
            this.body = pieces;
        }
    }
}
