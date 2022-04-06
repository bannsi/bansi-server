package com.gotgam.bansi.controller;

import java.util.List;

import com.gotgam.bansi.DTO.FilterDTO;
import com.gotgam.bansi.DTO.ThumbnailDTO.ListThumbNailDTOResponse;
import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.service.ThumbNailService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<ListThumbNailDTOResponse> findByUser(@PathVariable String userId) {
        List<ThumbNailDTO> thumbnails = thumbNailService.findThumbNailsByUserId(userId);
        return ResponseEntity.ok().body(new ListThumbNailDTOResponse("S00", "find thumbnails by username", thumbnails));
    }

    @PostMapping(value = "/filter/")
    public ResponseEntity<ListThumbNailDTOResponse> filterByKeywordAndWhoAndPlace(@RequestBody FilterDTO filterDto){
        List<ThumbNailDTO> thumbnails = thumbNailService.findByKeywords(filterDto.getWhoIds(), filterDto.getKeywordIds(), filterDto.getPlaceNames());
        return ResponseEntity.ok().body(new ListThumbNailDTOResponse("S00", "filtered thumbnails", thumbnails));
    }
    
}
