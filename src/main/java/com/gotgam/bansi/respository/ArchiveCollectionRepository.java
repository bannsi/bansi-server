package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.ArchiveCollection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveCollectionRepository extends CrudRepository<ArchiveCollection, Long> {
    
}
