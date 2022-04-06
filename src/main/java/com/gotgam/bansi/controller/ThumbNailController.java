package com.gotgam.bansi.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.ThumbNail;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.ThumbNailRepository;
import com.gotgam.bansi.service.ThumbNailService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;


@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/thumbnail/v1")
@RequiredArgsConstructor
public class ThumbNailController {
    private final ThumbNailService thumbNailService;
    private final ThumbNailRepository thumbNailRepository;
    private final PieceRepository pieceRepository;

    @RequestMapping(value="", method=RequestMethod.GET)
    @Transactional
    public String test() {
        List<Piece> pieces = pieceRepository.findAll();
        List<ThumbNail> thumbnails = pieces.stream().map(
            piece -> new ThumbNail(
                piece, 
                piece.getUser(), 
                piece.getImages().stream().filter(
                    image -> image.getThumbnail().equals(Boolean.TRUE)).findAny().get().getEncoded(),
                Set.copyOf(piece.getKeywords()),
                Set.copyOf(piece.getOpKeywords()),
                Set.copyOf(piece.getWhos()),
                piece.getPlace()
            ))
            .collect(Collectors.toList());
        thumbNailRepository.saveAll(thumbnails);
        return "test";
    }
    
}
