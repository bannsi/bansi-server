package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.KeywordDTO.KeywordResponse;
import com.gotgam.bansi.DTO.KeywordDTO.ListKeywordResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.service.KeywordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/pieces/v1/keyword")
public class KeywordController {
    @Autowired
    private KeywordRepository keywordRepository;
    
    @Autowired
    private KeywordService keywordService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ListKeywordResponse> listKeywords(){
        List<Keyword> keywords = keywordRepository.findAll();
        return ResponseEntity.ok().body(new ListKeywordResponse("S00", "keywords", keywords));
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<KeywordResponse> createKeyword(@RequestBody Keyword keyword){
        keywordRepository.save(keyword);
        return ResponseEntity.ok().body(new KeywordResponse("S00", "keyword is created", keyword));
    }

    @RequestMapping(value = "/{keywordId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteKeyword(@PathVariable Long keywordId){
        keywordService.deleteKeyword(keywordId);
        return ResponseEntity.ok().body(new ResponseDTO("delete", null));
    }
}
