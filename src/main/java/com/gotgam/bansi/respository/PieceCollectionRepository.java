package com.gotgam.bansi.respository;

import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.model.PieceCollection;

import org.springframework.data.repository.CrudRepository;

public interface PieceCollectionRepository extends CrudRepository<PieceCollection, Long> {
    Optional<PieceCollection> findByCollectionId(Long collectionId);
    List<PieceCollection> findByUserKakaoId(String userId);
}
