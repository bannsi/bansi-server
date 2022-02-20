package com.gotgam.bansi.controller;

import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
        
    }
}
