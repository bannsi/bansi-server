package com.gotgam.bansi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.DTO.ItemDTO.ItemRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.service.PieceCollectionService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class CollectionControllerTest {
    private MockMvc mvc;

    @Value("${test.token}")
    private String testToken;
    
    @Autowired
    private PieceCollectionService collectionService;

    @Autowired
    private PieceService pieceService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new CollectionController(jwtUtil, collectionService))
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }   

    @Test
    @DisplayName("collection create test")
    public void createCollectionTest() throws Exception{
        
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", "test collection");
        reqBody.put("coverImage", "test cover");
        List<ItemRequest> items = new ArrayList<>();
        for(int i = 1; i <= 3; i++){

            Piece piece = pieceService.savePiece(
                new PieceRequest(
                    new Date(), 
                    "test content", 
                    2.333, 
                    4.333, 
                    "busan", 
                    "test city", 
                    "www.naver.com", 
                    new ArrayList<ImageRequest>(), 
                    new ArrayList<Long>(),
                    new ArrayList<Long>(),
                    new ArrayList<Long>(),
                    "busan"), "zzzinho");
            ItemRequest itemDto = new ItemRequest();
            itemDto.setContent("test item" + String.valueOf(i));
            itemDto.setPieceId(piece.getPieceId());
            itemDto.setOrederNum(i);
            itemDto.setDate(new Date());

            items.add(itemDto);
        }
        reqBody.put("items", items);
        reqBody.put("startDate", "2022-03-22");
        reqBody.put("endDate", "2022-03-22");
        reqBody.put("place", "busan");

        Gson gson = new Gson();
        JsonObject json = gson.toJsonTree(reqBody).getAsJsonObject();

        final ResultActions actions = mvc.perform(
            post("/collections/v1/")
                .header(HttpHeaders.AUTHORIZATION, testToken)
                .contentType(MediaType.APPLICATION_JSON)  
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json.toString())
        );

        actions.andExpectAll(status().isOk());
    }
}
