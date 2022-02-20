package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.OptionalKeyword;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalKeywordRepository extends CrudRepository<OptionalKeyword, Long> {
    List<OptionalKeyword> findAll();    
    List<OptionalKeyword> findAllById(List<Long> ids);
}
