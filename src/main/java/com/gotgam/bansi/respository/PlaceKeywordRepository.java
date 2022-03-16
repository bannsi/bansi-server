package com.gotgam.bansi.respository;

import java.util.Optional;

import com.gotgam.bansi.model.PlaceKeyword;

import org.springframework.data.repository.CrudRepository;

public interface PlaceKeywordRepository extends CrudRepository<PlaceKeyword, Long> {   
    Optional<PlaceKeyword> findByName(String name);
}
