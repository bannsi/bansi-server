package com.gotgam.bansi.DTO;

import com.gotgam.bansi.model.Item;

import lombok.Getter;

public class ItemDTO {
    @Getter
    public static class ItemResponse extends ResponseDTO {
        private Item body;
        public ItemResponse(String code, String message, Item item){
            super(code, message);
            this.body = item;
        }
    }    
}
