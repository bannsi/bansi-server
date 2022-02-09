package com.gotgam.bansi.controller;

import java.io.IOException;
import java.util.List;

import com.gotgam.bansi.DTO.PieceCollectionDTO.ListPieceCollectionResponse;
import com.gotgam.bansi.DTO.PieceCollectionDTO.PieceCollectionRequest;
import com.gotgam.bansi.DTO.PieceCollectionDTO.PieceCollectionResponse;
import com.gotgam.bansi.model.PieceCollection;
import com.gotgam.bansi.respository.ItemRepository;
import com.gotgam.bansi.respository.PieceCollectionRepository;
import com.gotgam.bansi.service.PieceCollectionService;
import com.gotgam.bansi.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/collections/v1")
public class CollectionController {
    private final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PieceCollectionService collectionService;

    @Autowired
    private PieceCollectionRepository collectionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<PieceCollectionResponse> createCollection(@RequestHeader HttpHeaders headers, @RequestBody PieceCollectionRequest collectionRequest){
        String token = headers.getFirst("Authorization").substring(7);
        String userId = jwtUtil.getUsernameFromToken(token);
        PieceCollection pieceCollection = collectionService.saveCollection(userId, collectionRequest);

        return ResponseEntity.ok().body(new PieceCollectionResponse("S00", "created", pieceCollection));
    }

    @RequestMapping(value = "/{collectionId}", method = RequestMethod.GET)
    public ResponseEntity<PieceCollectionResponse> getCollection(@PathVariable Long collectionId) throws IOException{
        // PieceCollectionResponse collectionResponse = null;
        // // collectionResponse = collectionService.getCollection(collectionId);    
        // try {
        //     collectionResponse = collectionService.getCollection(collectionId);    
        // } catch (Exception e) {
        //     ResponseEntity.badRequest().body(new ResponseDTO("wrong collection id: " + String.valueOf(collectionId), null));
        // }
        PieceCollection pieceCollection = collectionService.getCollection(collectionId);
        return ResponseEntity.ok().body(new PieceCollectionResponse("S00", "get collection", pieceCollection));
    }

    @RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
    public ResponseEntity<ListPieceCollectionResponse> listCollection(@PathVariable String userId) {
        List<PieceCollection> collections = collectionService.listCollections(userId);
        // List<PieceCollectionResponse> collectionResponses = new ArrayList<>();
        // for(PieceCollection collection : collections){
        //     try{
        //         collectionResponses.add(collectionService.getCollection(collection.getCollectionId()));
        //     } catch (Exception e){

        //     }
        // }
        return ResponseEntity.ok().body(new ListPieceCollectionResponse("S00", "collection list", collections));
    }
    
}
