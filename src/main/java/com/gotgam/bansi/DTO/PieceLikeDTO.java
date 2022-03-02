package com.gotgam.bansi.DTO;

import lombok.Getter;

public class PieceLikeDTO {
    @Getter
    public static class PieceLikeResponse extends ResponseDTO {
        private Long likeCount;
        public PieceLikeResponse(String code, String message, Long likeCount){
            super(code, message);
            this.likeCount = likeCount;
        }
    }
}
