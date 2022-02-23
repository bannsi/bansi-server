package com.gotgam.bansi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PieceService pieceService;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new PieceController(jwtUtil, pieceService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    public void pieceCreateTest() throws Exception {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", "test title");
        reqBody.put("date", "2022-12-02");
        reqBody.put("content", "test content");
        reqBody.put("latitude", 2.303030);
        reqBody.put("longitude", 49.393939);
        reqBody.put("address", "부산광역시 금정구");
        reqBody.put("addressDetail", "금단로38 협성");
        reqBody.put("placeUrl", "https://www.naver.com");
        List<Long> keywords = new ArrayList<>();
        keywords.add(1L);
        keywords.add(2L);
        reqBody.put("keywords", keywords);
        List<Long> opKeywords = new ArrayList<>();
        opKeywords.add(1L);
        opKeywords.add(2L);
        reqBody.put("optionalKeywords", opKeywords);
        List<Long> whos = new ArrayList<>();
        whos.add(1L);
        whos.add(2L);
        reqBody.put("whos", whos);
        Gson gson = new Gson();
        JsonObject json = gson.toJsonTree(reqBody).getAsJsonObject();
        final ResultActions actions = mvc.perform(
            post("/piece/v1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json.toString()));

        actions.andExpectAll(status().isOk());        
    }
}
