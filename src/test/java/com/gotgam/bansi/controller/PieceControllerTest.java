package com.gotgam.bansi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.gotgam.bansi.DTO.KeywordDTO.KeywordRequest;
import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.service.KeywordService;
import com.gotgam.bansi.service.OptionalKeywordService;
import com.gotgam.bansi.service.PieceLikeService;
import com.gotgam.bansi.service.PieceService;
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
import org.springframework.mock.web.MockMultipartFile;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PieceService pieceService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private OptionalKeywordService optionalKeywordService;

    @Autowired
    private WhoKeywordService whoKeywordService;
    
    @Autowired
    private PieceLikeService pieceLikeService;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new PieceController(jwtUtil, pieceService, pieceLikeService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    public void pieceCreateTest() throws Exception {
        List<Long> keywords = new ArrayList<>();
        keywords.add(keywordService.createKeyword(new KeywordRequest("test keyword1")).getId());
        keywords.add(keywordService.createKeyword(new KeywordRequest("test keyword2")).getId());
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
        
        MockMultipartFile image1 = new MockMultipartFile("images", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        final ResultActions actions = mvc.perform(
            multipart("/pieces/v1/").file(image1).file(image2)
                                    .header(HttpHeaders.AUTHORIZATION, testToken)
                                    .param("date", "2022-12-12")
                                    .param("content", "test content")
                                    .param("latitude", "2.39494")
                                    .param("longitude", "49.393939")
                                    .param("address", "부산광역시 금정구")
                                    .param("addressDetail", "금단로38 협성")
                                    .param("placeUrl", "https://www.naver.com")
                                    .param("keywords", keywordReq)
                                    .param("optionalKeywords", opKeywordReq)
                                    .param("whos", whoKeywordReq)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .characterEncoding("UTF-8")
        );
    
        actions.andExpectAll(status().isOk());        
    }
    
    @Test
    public void listPiece() throws Exception {
        final ResultActions actions = mvc.perform(
            get("/pieces/v1")
                .header(HttpHeaders.AUTHORIZATION, testToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );
        actions.andExpect(status().isOk());
    }
}
