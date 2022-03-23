package com.gotgam.bansi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gotgam.bansi.DTO.ItemDTO.ItemRequest;
import com.gotgam.bansi.DTO.PieceCollectionDTO.PieceCollectionRequest;
import com.gotgam.bansi.model.Item;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.ItemRepository;
import com.gotgam.bansi.respository.PieceCollectionRepository;
import com.gotgam.bansi.respository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PieceCollectionServiceImpl implements PieceCollectionService {
    private final PieceCollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PieceService pieceService;
    private final PlaceKeywordService placeKeywordService;

    @Override
    public PieceCollection getCollection(Long collectionId) {
        PieceCollection pieceCollection = collectionRepository.findByCollectionId(collectionId).orElseThrow(() -> new NotFoundException("wrong collectionId"));
        return pieceCollection;
    }

    @Override
    public PieceCollection saveCollection(String userId, PieceCollectionRequest collectionRequest){
        Map<LocalDate, List<Item>> dateItemMap = new HashMap<>();
        List<Item> totalItems = new ArrayList<>();

        for(ItemRequest itemDto : collectionRequest.getItems()){
            Piece piece = pieceService.getPieceByPieceId(itemDto.getPieceId());
            Item item = new Item(itemDto.getContent(), piece, itemDto.getOrederNum(), itemDto.getDate());
            if(dateItemMap.containsKey(item.getDate())){
                dateItemMap.get(item.getDate()).add(item);
            } else {
                List<Item> items = new ArrayList<>();
                items.add(item);
                dateItemMap.put(item.getDate(), items);
            }
            totalItems.add(item);
        }
        itemRepository.saveAll(totalItems);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("wrong userId"));
        PieceCollection pieceCollection = new PieceCollection();
        pieceCollection.setTitle(collectionRequest.getTitle());
        pieceCollection.setUser(user);
        pieceCollection.setStartDate(collectionRequest.getStartDate());
        pieceCollection.setEndDate(collectionRequest.getEndDate());
        pieceCollection.setItems(totalItems);
        pieceCollection.setCoverImage(collectionRequest.getCoverImage());
        pieceCollection.setPlace(placeKeywordService.getOrCreate(collectionRequest.getPlace()));
        itemRepository.saveAll(totalItems);
        
        collectionRepository.save(pieceCollection);
        return pieceCollection;
    }

    @Override
    public List<PieceCollection> listCollections(String userId){
        List<PieceCollection> collections = collectionRepository.findByUser_KakaoId(userId);
        return collections;   
    }

    @Override
    public List<PieceCollection> findByPlace(String placeName){
        PlaceKeyword place = placeKeywordService.findByName(placeName);
        List<PieceCollection> collections = collectionRepository.findByPlace(place);
        return collections;
    }
}
