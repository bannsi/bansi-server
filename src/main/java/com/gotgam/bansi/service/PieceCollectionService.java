package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.PieceCollectionDTO.PieceCollectionRequest;
import com.gotgam.bansi.model.PieceCollection;

public interface PieceCollectionService {
    PieceCollection getCollection(Long collectionId);   
    PieceCollection saveCollection(String userId, PieceCollectionRequest collectionRequest);
    List<PieceCollection> listCollections(String userId);
    List<PieceCollection> findByPlace(String placeName);
}
