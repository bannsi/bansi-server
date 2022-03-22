package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final KeywordRepository keywordRepository;

    @Override
    public List<Keyword> listKeywords(List<Long> ids){
        List<Keyword> keywords = keywordRepository.findAllById(ids);
        return keywords;
    }  

    @Override
    public List<Keyword> findAll(){
        return keywordRepository.findAll();
    }

    @Override
    public void deleteKeyword(Long keywordId){
        keywordRepository.deleteById(keywordId);
    }

    @Override
    public List<Keyword> listKeywordsById(List<Long> ids){
        return keywordRepository.findAllById(ids);
    }

    @Override
    public Keyword createKeyword(String name){
        Keyword keyword = new Keyword(name);
        return keywordRepository.save(keyword);
    }
}
