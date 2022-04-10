package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.CollectionDTO.PieceCollectionDTO;
import com.gotgam.bansi.DTO.CollectionDTO.PieceCollectionRequest;
import com.gotgam.bansi.model.PieceCollection;

public interface PieceCollectionService {
    PieceCollection getCollection(Long collectionId);   
    PieceCollection saveCollection(String userId, PieceCollectionRequest collectionRequest);
    List<PieceCollectionDTO> listCollections(String userId);
    List<PieceCollectionDTO> findByPlace(String placeName);
}
