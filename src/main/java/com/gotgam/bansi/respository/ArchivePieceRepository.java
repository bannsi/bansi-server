package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.ArchivePiece;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivePieceRepository extends CrudRepository<ArchivePiece, Long> {
    
}
