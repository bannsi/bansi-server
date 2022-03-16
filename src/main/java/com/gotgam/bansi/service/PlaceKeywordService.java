package com.gotgam.bansi.service;

import java.util.Optional;

import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.respository.PlaceKeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceKeywordService {
    private final PlaceKeywordRepository placeKeywordRepository;

    PlaceKeyword getOrCreate(String name){
        Optional<PlaceKeyword> opPlace = placeKeywordRepository.findByName(name);
        PlaceKeyword placeKeyword;
        if(!opPlace.isPresent()){
            placeKeyword = placeKeywordRepository.save(new PlaceKeyword(name));
        } else {
            placeKeyword = opPlace.get();
        }
        return placeKeyword;
    }
}
