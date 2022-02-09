package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.Item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    
}
