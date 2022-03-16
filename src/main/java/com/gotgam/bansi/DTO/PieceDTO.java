package com.gotgam.bansi.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.DTO.ImageDTO.ImageResponse;
import com.gotgam.bansi.DTO.ImageDTO.PieceThumbnail;
import com.gotgam.bansi.model.Image;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.WhoKeyword;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PieceDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PieceRequest {
        @DateTimeFormat(iso = ISO.DATE)
        private Date date;
        @NotBlank
        private String content;
        @NotNull
        private Double latitude;
        @NotNull
        private Double longitude;
        @NotBlank
        private String address;
        @NotBlank
        private String addressDetail;
        @NotBlank
        private String placeUrl;
        @NotEmpty
        private List<ImageRequest> images;
        @NotEmpty
        private List<Long> keywords;
        private List<Long> optionalKeywords;
        @NotEmpty
        private List<Long> whos;
        @NotBlank
        private String place;
    }    

    @Getter
    public static class PieceResponse extends ResponseDTO {
        private PieceResponseBody body;
        public PieceResponse(String code, String message, Piece piece){
            super(code, message);
            this.body = new PieceResponseBody(piece);
        }
    }

    @Getter
    public static class ListPieceResponse extends ResponseDTO {
        private List<PieceResponseBody> body;
        public ListPieceResponse(String code, String message, List<Piece> pieces){
            super(code, message);
            List<PieceResponseBody> body = new ArrayList<>();
            for(Piece piece : pieces){
                body.add(new PieceResponseBody(piece));
            }
            this.body = body;
        }
    }
    @Getter
    public static class PieceResponseBody{
        private Long pieceId;
        private String content;
        private Date date;
        private Double latitude;
        private Double longitude;
        private String address;
        private String addressDetail; 
        private String placeUrl;
        private List<Keyword> keywords;
        private List<OptionalKeyword> optionalKeywords;
        private List<WhoKeyword> whos;
        private List<ImageResponse> images;
        private String place;

        public PieceResponseBody(Piece piece){
            this.pieceId = piece.getPieceId();
            this.content = piece.getContent();
            this.date = piece.getDate();
            this.latitude = piece.getLatitude();
            this.longitude = piece.getLongitude();
            this.address = piece.getAddress();
            this.addressDetail = piece.getAddressDetail();
            this.placeUrl = piece.getPlaceUrl();
            this.keywords = piece.getKeywords(); 
            this.optionalKeywords = piece.getOpKeywords();
            this.whos = piece.getWhos();
            this.images = new ArrayList<>();
            for(Image image : piece.getImages()){
                this.images.add(new ImageResponse(image.getEncoded(), image.getThumbnail()));
            }
            if(piece.getPlace() != null)
                this.place = piece.getPlace().getName();
        }
    }
    @Getter
    public static class PieceThumbnailResponse extends ResponseDTO {
        private List<PieceThumbnail> body;
        public PieceThumbnailResponse(String code, String message, List<PieceThumbnail> body){
            super(code, message);
            this.body = body;
        }
    }
}
