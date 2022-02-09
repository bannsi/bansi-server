package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.WhoKeyword;

import lombok.Getter;

public class WhoKeywordDTO {
    @Getter
    public static class WhoKeywordResponse extends ResponseDTO {
        private WhoKeyword body;
        public WhoKeywordResponse(String code, String message, WhoKeyword whoKeyword){
            super(code, message);
            this.body = whoKeyword;
        }
    }

    @Getter
    public static class ListWhowKeywordResponse extends ResponseDTO {
        private List<WhoKeyword> body;
        public ListWhowKeywordResponse(String code, String message, List<WhoKeyword> whoKeywords){
            super(code, message);
            this.body = whoKeywords;
        }
    }
}
