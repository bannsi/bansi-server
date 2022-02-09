package com.gotgam.bansi.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;

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
        private String keywords;
        private String whos;        
    }    

    public static class PieceResponse extends ResponseDTO {
        private Long pieceId;
        private User user;
        private String content;
        private Double latitude;
        private Double longitude;
        private String address;
        private String addressDetail;
        private String placeUrl;
        private Date date;
        private Date createdAt;
        private List<String> images;
        private List<String> keywords;
        private List<String> whos;   
        
        public PieceResponse(Piece piece, User user, List<String> images, List<Keyword> keywords, List<WhoKeyword> whos){
            this.pieceId = piece.getPieceId();
            this.user = user;
            this.content = piece.getContent();
            this.latitude = piece.getLatitude();
            this.longitude = piece.getLongitude();
            this.date = piece.getDate();
            this.address = piece.getAddress();
            this.addressDetail = piece.getAddressDetail();
            this.placeUrl = piece.getPlaceUrl();
            this.createdAt = piece.getCreatedAt();
            this.keywords = new ArrayList<String>();
            for(Keyword keyword : keywords){
                this.keywords.add(keyword.getName());
            }
            this.whos = new ArrayList<String>();
            for(WhoKeyword whoKeyword : whos){
                this.whos.add(whoKeyword.getWho());
            }
            this.images = images;
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
