package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeywordService {
    @Autowired
    private KeywordRepository keywordRepository;

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
}
