package com.gotgam.bansi.controller;

import javax.validation.Valid;

import com.gotgam.bansi.DTO.OptionalKeywordDTO.ListOptionalKeywordResponse;
import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.service.OptionalKeywordService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/opKeyword/v1")
public class OptionalKeywordController {
    private OptionalKeywordService opKeywordService;
    
    public OptionalKeywordController(OptionalKeywordService opKeywordService){
        this.opKeywordService = opKeywordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ListOptionalKeywordResponse> listOpKeywords(){
        return ResponseEntity.ok().body(
            new ListOptionalKeywordResponse(
                "S00", 
                "list optional keywords", 
                opKeywordService.listOpKeyword()));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<OptionalKeywordResponse> createOpKeyword(@Valid @RequestBody OptionalKeywordRequest opKeywordDto){
        OptionalKeyword opKeyword = opKeywordService.createOpKeyword(opKeywordDto);
        return ResponseEntity.ok().body(
            new OptionalKeywordResponse(
                "S00", 
                "create optional keyword", 
                opKeyword)
        );
    }

    @RequestMapping(value = "/{opkeywordId}/", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteOpKeyword(@PathVariable Long opkeywordId){
        opKeywordService.deleteOpKeyword(opkeywordId);
        return ResponseEntity.ok().body(new ResponseDTO("S00", "optional keyword is deleted"));
    }
}