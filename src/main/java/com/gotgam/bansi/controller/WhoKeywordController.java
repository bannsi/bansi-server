package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.DTO.WhoKeywordDTO.ListWhowKeywordResponse;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordResponse;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.service.WhoKeywordService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/pieces/v1/who")
public class WhoKeywordController {
    private final WhoKeywordService whoKeywordService;

    public WhoKeywordController(WhoKeywordService whoKeywordService){
        this.whoKeywordService = whoKeywordService;
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<ListWhowKeywordResponse> listWhos() {
        List<WhoKeyword> whoKeywords = whoKeywordService.listWho();
        return ResponseEntity.ok().body(new ListWhowKeywordResponse("S00", "list who keyword", whoKeywords));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<WhoKeywordResponse> createWho(@RequestBody WhoKeywordRequest whoKeywordDto){
        WhoKeyword whoKeyword = whoKeywordService.createWhoKeyword(whoKeywordDto);
        return ResponseEntity.ok().body(new WhoKeywordResponse("S00", "saved", whoKeyword));
    }   

    @RequestMapping(value = "/{whoId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteWho(@PathVariable Long whoId){
        whoKeywordService.deleteWho(whoId);
        return ResponseEntity.ok().body(new ResponseDTO("deleted", null));
    }
}
