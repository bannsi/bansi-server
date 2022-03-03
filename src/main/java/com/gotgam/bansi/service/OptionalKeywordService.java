package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.model.OptionalKeyword;

public interface OptionalKeywordService {
    OptionalKeyword createOpKeyword(OptionalKeywordRequest opKeywordDto);
    List<OptionalKeyword> listOpKeyword();
    void deleteOpKeyword(Long id);
    OptionalKeyword updateOpKeyword(Long id, OptionalKeywordRequest opKeywordDto);
}
