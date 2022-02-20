package com.gotgam.bansi.controller;

import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.DTO.WhoKeywordDTO.ListWhowKeywordResponse;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordResponse;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.WhoKeywordRepository;

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
@RequestMapping(value = "/pieces/v1/who")
public class WhoKeywordController {
    @Autowired
    private WhoKeywordRepository whoKeywordRepository;

    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<ListWhowKeywordResponse> listWhos() {
        List<WhoKeyword> whoKeywords = whoKeywordRepository.findAll();
        return ResponseEntity.ok().body(new ListWhowKeywordResponse("S00", "list who keyword", whoKeywords));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<WhoKeywordResponse> createWho(@RequestBody WhoKeyword who){
        whoKeywordRepository.save(who);
        return ResponseEntity.ok().body(new WhoKeywordResponse("S00", "saved", who));
    }   

    @RequestMapping(value = "/{whoId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteWho(@PathVariable Long whoId){
        Optional<WhoKeyword> whoKeyword = whoKeywordRepository.findById(whoId);
        whoKeywordRepository.delete(whoKeyword.get());
        return ResponseEntity.ok().body(new ResponseDTO("deleted", null));
    }
}
