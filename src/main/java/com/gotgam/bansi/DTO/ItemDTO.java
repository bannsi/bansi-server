package com.gotgam.bansi.DTO;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.gotgam.bansi.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ItemDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemRequest {
        private String content;
        @Positive
        private Long pieceId;
        @NotNull
        private Integer orederNum;
        private LocalDate date;
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
