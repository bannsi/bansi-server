package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.ThumbNail;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class ThumbnailDTO {
    @Getter
    @NoArgsConstructor
    public static class ListThumbnailResponse extends ResponseDTO {
        List<ThumbNail> body;
        public ListThumbnailResponse(String code, String message, List<ThumbNail> body){
            super(code, message);
            this.body = body;
        }
    }
}
