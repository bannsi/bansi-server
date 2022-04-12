package com.gotgam.bansi.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotgam.bansi.DTO.CommentDTO.CommentRequest;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.service.CommentService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.CharacterEncodingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    private MockMvc mvc;
    private StopWatch stopWatch;
    @Autowired private CommentService commentService;
    @Autowired private PieceRepository pieceRepository;
    @Autowired private PieceService pieceService;
    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;

    @Value("${test.token}")
    private String testToken;
    
    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new CommentController(commentService, pieceService, userService, jwtUtil))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
        stopWatch = new StopWatch();
    }

    @Test
    @DisplayName("comment create test")
    public void createCommentTest() throws Exception {
        Piece piece = pieceRepository.findAll().get(0);
        CommentRequest reqBody = new CommentRequest();
        reqBody.setComment("test comment");
        String json = new ObjectMapper().writeValueAsString(reqBody);
        stopWatch.start();
        final ResultActions actions = mvc.perform(
            post("/v1/comment/piece/" + piece.getPieceId() + "/").header(HttpHeaders.AUTHORIZATION, testToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json));
        stopWatch.stop();;
        System.out.println(stopWatch.prettyPrint());
        actions.andExpect(status().isOk());
        actions.andDo(print());
    }

    @Test
    @DisplayName("댓글 리스트 테스트")
    public void listCommentTest() throws Exception {
        Piece piece = pieceRepository.findAll().get(0);

        stopWatch.start();
        final ResultActions actions = mvc.perform(
            get("/v1/comment/peice/" + piece.getPieceId())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        actions.andExpect(status().isOk());
        actions.andDo(print());
    }
}
