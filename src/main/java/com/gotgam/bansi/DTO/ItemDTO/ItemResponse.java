package com.gotgam.bansi.DTO.ItemDTO;

import com.gotgam.bansi.DTO.ResponseDTO;

import lombok.Getter;

@Getter
public class ItemResponse extends ResponseDTO{
    private ItemDTO body;
        public ItemResponse(String code, String message, ItemDTO item){
            super(code, message);
            this.body = item;
        }
}
