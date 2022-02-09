package com.gotgam.bansi.DTO;

import com.gotgam.bansi.model.User;

import lombok.Getter;

public class AccountDTO {
    @Getter
    public static class UserResponse extends ResponseDTO{
        private User body;
        public UserResponse(String code, String message, User user){
            super(code, message);
            this.body = user;
        }
    }        
}
