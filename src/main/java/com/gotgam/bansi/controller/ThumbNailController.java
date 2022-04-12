package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.FilterDTO;
import com.gotgam.bansi.DTO.ThumbnailDTO.ListThumbNailDTOResponse;
import com.gotgam.bansi.DTO.ThumbnailDTO.PageThumbNailResponse;
import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.service.ThumbNailService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;


@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/piece/v1")
@RequiredArgsConstructor
public class ThumbNailController {
    private final ThumbNailService thumbNailService;

    @RequestMapping(value="/ramdom", method=RequestMethod.GET)
    public ResponseEntity<ListThumbNailDTOResponse> randomThmnails() {
        List<ThumbNailDTO> thumbnails = thumbNailService.findRandomThumbNails();
        return ResponseEntity.ok().body(new ListThumbNailDTOResponse("S00", "random thumbnails", thumbnails));
    }
    
    @RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
    public ResponseEntity<PageThumbNailResponse> findByUser(@PathVariable String userId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<ThumbNailDTO> thumbnails = thumbNailService.findThumbNailsByUserId(userId, PageRequest.of(page, size));
        return ResponseEntity.ok().body(new PageThumbNailResponse("S00", "find thumbnails by username", thumbnails));
    }

    @PostMapping(value = "/filter/")
    public ResponseEntity<PageThumbNailResponse> filterByKeywordAndWhoAndPlace(@RequestBody FilterDTO filterDto, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<ThumbNailDTO> thumbnails = thumbNailService.findByKeywords(filterDto.getWhoIds(), filterDto.getKeywordIds(), filterDto.getPlaceNames(), PageRequest.of(page, size));
        return ResponseEntity.ok().body(new PageThumbNailResponse("S00", "filtered thumbnails", thumbnails));
    }
    
}
