package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.ThumbNail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ThumbnailDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ListThumbnailResponse extends ResponseDTO {
        List<ThumbNail> body;
        public ListThumbnailResponse(String code, String message, List<ThumbNail> body){
            super(code, message);
            this.body = body;
        }
        public ListThumbnailResponse(String code, String message){
            super(code,message);
        }
    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThumbNailDTO{
        private Long pieceId;
        private String userId;
        private String encoded;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ListThumbNailDTOResponse extends ResponseDTO {
        List<ThumbNailDTO> body;
        public ListThumbNailDTOResponse(String code, String message, List<ThumbNailDTO> body){
            super(code, message);
            this.body = body;
        }
    }
}
