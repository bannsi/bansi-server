package com.gotgam.bansi.DTO;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.gotgam.bansi.model.OptionalKeyword;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OptionalKeywordDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class OptionalKeywordRequest{
        @NotBlank
        private String name;
    }    
    @Getter
    public static class ListOptionalKeywordResponse extends ResponseDTO {
        private List<OptionalKeyword> body;
        public ListOptionalKeywordResponse(String code, String message, List<OptionalKeyword> opKeywords){
            super(code, message);
            this.body = opKeywords;
        }
    }
    @Getter
    public static class OptionalKeywordResponse extends ResponseDTO {
        private OptionalKeyword body;
        public OptionalKeywordResponse(String code, String message, OptionalKeyword opKeyword){
            super(code, message);
            this.body = opKeyword;
        }
    }
}
