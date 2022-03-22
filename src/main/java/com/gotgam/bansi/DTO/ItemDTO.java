package com.gotgam.bansi.DTO;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.gotgam.bansi.model.Item;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItemDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ItemRequest {
        private String content;
        @Positive
        private Long pieceId;
        @NotNull
        private Integer orederNum;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
    }
    @Getter
    public static class ItemResponse extends ResponseDTO {
        private Item body;
        public ItemResponse(String code, String message, Item item){
            super(code, message);
            this.body = item;
        }
    }    
}
