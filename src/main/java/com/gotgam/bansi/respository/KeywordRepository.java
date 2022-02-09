package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.Keyword;

import org.springframework.data.repository.CrudRepository;
public interface KeywordRepository extends CrudRepository<Keyword, Long> {
    List<Keyword> findAll();
}
