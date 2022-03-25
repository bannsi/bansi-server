package com.gotgam.bansi.service;

import com.gotgam.bansi.model.User;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired private UserService userService;
    @Autowired private PieceService pieceService;
    @Autowired private JwtUtil jwtUtil;
    
    @Value("${test.token}")
    private String testToken;

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createCommentTest(){
        String userId = jwtUtil.getUsernameFromTokenStr(testToken);
        User user = userService.getUserFromId(userId);
    }
}
