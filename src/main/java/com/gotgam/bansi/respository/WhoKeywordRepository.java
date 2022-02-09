package com.gotgam.bansi.respository;

import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.model.WhoKeyword;

import org.springframework.data.repository.CrudRepository;

public interface WhoKeywordRepository extends CrudRepository<WhoKeyword, Long> {
    Optional<WhoKeyword> findById(Long whoId);
    List<WhoKeyword> findAll();
}
