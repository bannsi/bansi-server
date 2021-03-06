package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.Keyword;

public interface KeywordService {
    List<Keyword> listKeywords(List<Long> ids);
    void deleteKeyword(Long keywordId);
    List<Keyword> listKeywordsById(List<Long> ids);
    Keyword createKeyword(String name);
    List<Keyword> findAll();
}
