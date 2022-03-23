package com.gotgam.bansi.service;


import java.util.List;

import com.gotgam.bansi.DTO.OptionalKeywordDTO.OptionalKeywordRequest;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.respository.OptionalKeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionalKeywordServiceImpl implements OptionalKeywordService {
    private final OptionalKeywordRepository repository;

    @Override
    public OptionalKeyword createOpKeyword(OptionalKeywordRequest opKeywordDto){
        OptionalKeyword opKeyword = new OptionalKeyword();
        opKeyword.setName(opKeywordDto.getName());
        return repository.save(opKeyword);
    }

    @Override
    public List<OptionalKeyword> listOpKeyword() {
        return repository.findAll();
    }

    @Override
    public void deleteOpKeyword(Long id){
        repository.deleteById(id);
    }

    @Override
    public OptionalKeyword updateOpKeyword(Long id, OptionalKeywordRequest opKeywordDto){
        OptionalKeyword opKeyword = repository.findById(id).orElseThrow(() -> new NotFoundException("worng optional keyword id"));
        opKeyword.setName(opKeywordDto.getName());
        return opKeyword;
    }
}
