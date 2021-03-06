package com.gotgam.bansi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.service.KeywordService;
import com.gotgam.bansi.service.OptionalKeywordService;
import com.gotgam.bansi.service.PieceLikeService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.ThumbNailService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.service.WhoKeywordService;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.filter.CharacterEncodingFilter;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PieceControllerTest {
    private MockMvc mvc;
    
    @Value("${test.token}")
    private String testToken;

    @Autowired private JwtUtil jwtUtil;
    @Autowired private PieceService pieceService;
    @Autowired private KeywordService keywordService;
    @Autowired private OptionalKeywordService optionalKeywordService;
    @Autowired private WhoKeywordService whoKeywordService;
    @Autowired private PieceLikeService pieceLikeService;
    @Autowired private UserService userService;
    @Autowired private ThumbNailService thumbNailService;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new PieceController(jwtUtil, pieceService, pieceLikeService, userService, thumbNailService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    public void pieceCreateTest() throws Exception {
        List<Long> keywords = new ArrayList<>();
        keywords.add(keywordService.createKeyword("test keyword1").getId());
        keywords.add(keywordService.createKeyword("test keyword2").getId());
        List<Long> opKeywords = new ArrayList<>();
        opKeywords.add(optionalKeywordService.createOpKeyword(new OptionalKeywordRequest("test opkey1")).getId());
        List<Long> whos = new ArrayList<>();
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who1")).getId());
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who2")).getId());
        
        String keywordReq = keywords.toString();
        keywordReq = keywordReq.substring(1, keywordReq.length()-1);
        String opKeywordReq = opKeywords.toString();
        opKeywordReq = opKeywordReq.substring(1, opKeywordReq.length()-1);
        String whoKeywordReq = whos.toString();
        whoKeywordReq = whoKeywordReq.substring(1, whoKeywordReq.length()-1);
        List<ImageRequest> images = new ArrayList<>();
        images.add(new ImageRequest("test", true));
        images.add(new ImageRequest("test2", false));
        
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("date", "2022-02-02");
        reqBody.put("content", "test content");
        reqBody.put("latitude", 1.23333);
        reqBody.put("longitude", 24.3333);
        reqBody.put("address", "??????????????? ?????????");
        reqBody.put("addressDetail", "????????? 38");
        reqBody.put("placeUrl", "https://naver.com");
        reqBody.put("keywords", keywords);
        reqBody.put("optionalKeywords", opKeywords);
        reqBody.put("whos", whos);
        reqBody.put("images", images);
        reqBody.put("place", "busan");

        String json = new ObjectMapper().writeValueAsString(reqBody);
        final ResultActions actions = mvc.perform(
            post("/pieces/v1/")
                .header(HttpHeaders.AUTHORIZATION, testToken)
                .contentType(MediaType.APPLICATION_JSON)  
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                            
        );
    
        actions.andExpectAll(status().isOk());        
    }
    
    @Test
    public void pieceCreateFailTest() throws Exception {
        List<Long> keywords = new ArrayList<>();
        keywords.add(keywordService.createKeyword("test keyword1").getId());
        keywords.add(keywordService.createKeyword("test keyword2").getId());
        List<Long> opKeywords = new ArrayList<>();
        opKeywords.add(optionalKeywordService.createOpKeyword(new OptionalKeywordRequest("test opkey1")).getId());
        List<Long> whos = new ArrayList<>();
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who1")).getId());
        whos.add(whoKeywordService.createWhoKeyword(new WhoKeywordRequest("test who2")).getId());

        String keywordReq = keywords.toString();
        keywordReq = keywordReq.substring(1, keywordReq.length()-1);
        String opKeywordReq = opKeywords.toString();
        opKeywordReq = opKeywordReq.substring(1, opKeywordReq.length()-1);
        String whoKeywordReq = whos.toString();
        whoKeywordReq = whoKeywordReq.substring(1, whoKeywordReq.length()-1);
        List<String> images = new ArrayList<>();
        images.add("test");
        images.add("test2");
        
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("date", "2022-02-02");
        reqBody.put("content", "test content");
        reqBody.put("latitude", 1.23333);
        reqBody.put("longitude", 24.3333);
        reqBody.put("address", "  ");
        reqBody.put("addressDetail", "????????? 38");
        reqBody.put("placeUrl", " ");
        reqBody.put("keywords", keywords);
        reqBody.put("optionalKeywords", opKeywords);
        reqBody.put("whos", whos);
        reqBody.put("images", images);

        String json = new ObjectMapper().writeValueAsString(reqBody);
        final ResultActions actions = mvc.perform(
            post("/pieces/v1/")
                .header(HttpHeaders.AUTHORIZATION, testToken)
                .contentType(MediaType.APPLICATION_JSON)  
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
                            
        );
    
        actions.andExpectAll(status().isBadRequest());        
    }
}
