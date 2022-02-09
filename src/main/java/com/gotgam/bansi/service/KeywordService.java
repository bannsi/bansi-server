package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.respository.KeywordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class KeywordService {
    @Autowired
    private KeywordRepository keywordRepository;

    public List<String> listKeywords(List<Long> Ids){
        List<String> keywords = new ArrayList<>();
        for(Long id : Ids){
            Optional<Keyword> keyword = keywordRepository.findById(id);
            if(keyword.isPresent()){
                keywords.add(keyword.get().getName());
            }
        }
        return keywords;
    }

    public void deleteKeyword(Long keywordId){
        Optional<Keyword> keyword = keywordRepository.findById(keywordId);
        keywordRepository.delete(keyword.get());
    }
}
