package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.ArchiveLink;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveLinkRepository extends CrudRepository<ArchiveLink, Long> {
    
}
