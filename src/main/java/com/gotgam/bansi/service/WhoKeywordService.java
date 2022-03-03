package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WhoKeywordService {
    private final WhoKeywordRepository whoKeywordRepository;

    public WhoKeyword createWhoKeyword(WhoKeywordRequest whoKeywordDto){
        WhoKeyword whoKeyword = new WhoKeyword(whoKeywordDto.getName());
        return whoKeywordRepository.save(whoKeyword);
    }

    public void deleteWho(Long id){
        whoKeywordRepository.deleteById(id);
    }

    public List<WhoKeyword> listWho(){
        return whoKeywordRepository.findAll();
    }
}
