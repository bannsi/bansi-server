package com.gotgam.bansi.DTO;

import javax.validation.constraints.NotBlank;

import com.gotgam.bansi.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String user;
    private String content;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.user = comment.getUser().getKakaoId();
        this.content = comment.getContent();
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentRequest{
        @NotBlank
        private String comment;
    }

    @Getter
    public static class CommentResponse extends ResponseDTO {
        private CommentDTO body;
        public CommentResponse(String code, String message, CommentDTO body){
            super(code, message);
            this.body = body;
        }
    }
}
