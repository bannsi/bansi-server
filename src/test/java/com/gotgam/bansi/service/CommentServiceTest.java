package com.gotgam.bansi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gotgam.bansi.DTO.CommentDTO;
import com.gotgam.bansi.DTO.CommentDTO.CommentRequest;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Autowired private CommentService commentService;
    @Autowired private PieceRepository pieceRepository;
    @Autowired private JwtUtil jwtUtil;
    
    @Value("${test.token}")
    private String testToken;

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createCommentTest(){
        String userId = jwtUtil.getUsernameFromTokenStr(testToken);
        Piece piece = pieceRepository.findAll().get(0);
        CommentRequest commentRequest = new CommentRequest("댓글 생성");
        CommentDTO commentDTO = commentService.createCommnet(userId, piece.getPieceId(), commentRequest);
        assertEquals(commentDTO.getContent(), commentRequest.getComment());
    }
}
