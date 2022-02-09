package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.Keyword;

import lombok.Getter;

public class KeywordDTO {
    @Getter
    public static class KeywordResponse extends ResponseDTO {
        private Keyword body;
        public KeywordResponse(String code, String message, Keyword keyword){
            super(code, message);
            this.body = keyword;
        }
    }
    @Getter
    public static class ListKeywordResponse extends ResponseDTO {
        private List<Keyword> body;
        public ListKeywordResponse(String code, String message, List<Keyword> keywords){
            super(code, message);
            this.body = keywords;
        }
    }
}
