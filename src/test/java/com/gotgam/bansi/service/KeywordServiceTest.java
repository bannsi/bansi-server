package com.gotgam.bansi.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(MockitoExtension.class)
@Testcontainers
@SpringBootTest
public class KeywordServiceTest {
    private KeywordService keywordService;
    @Autowired
    private KeywordRepository keywordRepository;

    @BeforeEach
    public void setUp(){
        this.keywordService = new KeywordServiceImpl(keywordRepository);
    }

    private Keyword createMock(int i){
        return new Keyword("test" + String.valueOf(i));
    }

    @Test
    @DisplayName("keyword create test")
    public void testCreateKeyword(){
        Keyword keyword = keywordService.createKeyword("test");
        assertEquals(keyword.getName(), "test");
    }

    @Test
    @DisplayName("keyword list test")
    public void testListKeywrods(){
        List<Keyword> testKeywords = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            Keyword keyword = keywordService.createKeyword(createMock(i).getName());
            testKeywords.add(keyword);
            ids.add(keyword.getId());
        }
        List<Keyword> keywords = keywordService.listKeywords(ids);
        assertEquals(testKeywords.size(), keywords.size());
    }
}
