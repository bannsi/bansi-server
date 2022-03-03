package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.KeywordDTO.KeywordRequest;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public List<Keyword> listKeywords(List<Long> ids){
        List<Keyword> keywords = keywordRepository.findAllById(ids);
        return keywords;
    }

    public void deleteKeyword(Long keywordId){
        keywordRepository.deleteById(keywordId);
    }

    public List<Keyword> listKeywordsById(List<Long> ids){
        return keywordRepository.findAllById(ids);
    }

    public Keyword createKeyword(KeywordRequest keywordDto){
        Keyword keyword = new Keyword(keywordDto.getName());
        return keywordRepository.save(keyword);
    }
}
