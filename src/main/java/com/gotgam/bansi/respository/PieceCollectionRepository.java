package com.gotgam.bansi.respository;

import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.model.PlaceKeyword;

import org.springframework.data.repository.CrudRepository;

public interface PieceCollectionRepository extends CrudRepository<PieceCollection, Long> {
    Optional<PieceCollection> findByCollectionId(Long collectionId);
    List<PieceCollection> findByUser_KakaoId(String userId);
    List<PieceCollection> findByPlace(PlaceKeyword place);
}
