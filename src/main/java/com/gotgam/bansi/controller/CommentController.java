package com.gotgam.bansi.controller;

import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.service.CommentService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comment")
public class CommentController {
    private final CommentService commentService;
    private final PieceRepository pieceRepository;

    @RequestMapping(value="/piece/{pieceId}/", method=RequestMethod.POST)
    public String createCommnet(@PathVariable Long pieceId) {
        // Piece piece = pieceRepository.getById(pieceId);
        // commentService.createCommnet(user, piece, content)
        return "test";
    }
    
}
