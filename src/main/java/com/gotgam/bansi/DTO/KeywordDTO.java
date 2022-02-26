package com.gotgam.bansi.DTO;

import java.util.List;

import com.gotgam.bansi.model.Keyword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KeywordDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeywordRequest{
        private String name;
    }
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
