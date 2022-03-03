package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.KeywordDTO.KeywordRequest;
import com.gotgam.bansi.DTO.KeywordDTO.KeywordResponse;
import com.gotgam.bansi.DTO.KeywordDTO.ListKeywordResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.service.KeywordService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/pieces/v1/keyword")
public class KeywordController {
    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService){
        this.keywordService = keywordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ListKeywordResponse> listKeywords(){
        List<Keyword> keywords = keywordService.findAll();
        return ResponseEntity.ok().body(new ListKeywordResponse("S00", "keywords", keywords));
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<KeywordResponse> createKeyword(@RequestBody KeywordRequest keywordDto){
        Keyword keyword = keywordService.createKeyword(keywordDto);
        return ResponseEntity.ok().body(new KeywordResponse("S00", "keyword is created", keyword));
    }

    @RequestMapping(value = "/{keywordId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteKeyword(@PathVariable Long keywordId){
        keywordService.deleteKeyword(keywordId);
        return ResponseEntity.ok().body(new ResponseDTO("delete", null));
    }
}
