package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.WhoKeywordDTO.WhoKeywordRequest;
import com.gotgam.bansi.model.WhoKeyword;

public interface WhoKeywordService {
    WhoKeyword createWhoKeyword(WhoKeywordRequest whoKeywordDto);    
    void deleteWho(Long id);
    List<WhoKeyword> listWho();
}
