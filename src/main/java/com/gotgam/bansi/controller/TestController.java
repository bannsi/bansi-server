package com.gotgam.bansi.controller;

import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final KeywordRepository keywordRepository;
    private final WhoKeywordRepository whoKeywordRepository;
    private final PieceRepository pieceRepository;
}
