package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gotgam.bansi.DTO.PieceCollectionDTO.PieceCollectionRequest;
import com.gotgam.bansi.model.Item;
import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.ItemRepository;
import com.gotgam.bansi.respository.PieceCollectionRepository;
import com.gotgam.bansi.respository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PieceCollectionService {
    private static final Logger logger = LoggerFactory.getLogger(PieceCollectionService.class);
    
    @Autowired
    private PieceCollectionRepository collectionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private String getFileExtension(String filename){
        try {
            return filename.substring(filename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 %s 입니다.", filename));
        }
    }

    // private String createFilename(String originalFilename){
    //     return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    // }

    // public String downloadImage(String filename){
    //     return uploadService.getFileUrl(filename);
    // }

    // private String uploadImage(MultipartFile multipartFile, String filename){
    //     ObjectMetadata objectMetadata = new ObjectMetadata();
    //     objectMetadata.setContentLength(multipartFile.getSize());
    //     objectMetadata.setContentType(multipartFile.getContentType());
    //     try (InputStream inputStream = multipartFile.getInputStream()){
    //         uploadService.uploadFile(inputStream, objectMetadata, filename);
    //     } catch (IOException e) {
    //         throw new IllegalArgumentException(String.format("파일 변환 중 에러 발생  %s", multipartFile.getOriginalFilename()));
    //     }
    //     return uploadService.getFileUrl(filename);
    // }

    public PieceCollection getCollection(Long collectionId) {
        PieceCollection pieceCollection = collectionRepository.findByCollectionId(collectionId).orElseThrow(() -> new NotFoundException("wrong collectionId"));
        return pieceCollection;
    }

    public PieceCollection saveCollection(String userId, PieceCollectionRequest collectionRequest){
        Map<Date, List<Item>> dateItemMap = new HashMap<>();
        List<Item> totalItems = new ArrayList<>();
        for(Item item : collectionRequest.getItems()){
            if(dateItemMap.containsKey(item.getDate())){
                // item.setOrderNum(dateItemMap.get(item.getDate()).size());
                dateItemMap.get(item.getDate()).add(item);
            } else {
                // item.setOrderNum(0);
                List<Item> items = new ArrayList<>();
                items.add(item);
                dateItemMap.put(item.getDate(), items);
            }
            totalItems.add(item);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("wrong userId"));
        PieceCollection pieceCollection = new PieceCollection();
        pieceCollection.setTitle(collectionRequest.getTitle());
        pieceCollection.setUser(user);
        pieceCollection.setStartDate(collectionRequest.getStartDate());
        pieceCollection.setEndDate(collectionRequest.getEndDate());
        pieceCollection.setItems(totalItems);
        pieceCollection.setCoverImage(collectionRequest.getCoverImage());
        itemRepository.saveAll(totalItems);
        // String filename = UUID.randomUUID().toString() + "/" + createFilename(collectionRequest.getCoverImage().getOriginalFilename());
        
        collectionRepository.save(pieceCollection);   

        // uploadImage(collectionRequest.getCoverImage(), filename);

        return pieceCollection;
    }

    public List<PieceCollection> listCollections(String userId){
        List<PieceCollection> collections = collectionRepository.findByUserKakaoId(userId);
        return collections;   
    }
}
