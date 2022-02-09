package com.gotgam.bansi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amazonaws.services.kms.model.NotFoundException;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.UserRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class PieceService {
    @Autowired
    private PieceRepository pieceRepository;
    
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private WhoKeywordRepository whoKeywordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;


    private static final Logger logger = LoggerFactory.getLogger(PieceRepository.class);

    public Piece getPieceByPieceId(Long pieceId) throws IOException {
        Optional<Piece> piece = pieceRepository.findById(pieceId);
        if(!piece.isPresent()) throw new IOException();
        return piece.get();
    }

    public List<Piece> findPieceByUserId(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new NotFoundException("wrong user id");
        List<Piece> pieces = pieceRepository.findByUser(user.get());
        // List<PieceResponse> pieceResponses = new ArrayList<>();
        
        // for(Piece piece : pieces){
        //     pieceResponses.add(
        //         new PieceResponse(
        //             piece, 
        //             user.get(), 
        //             imageService.getImageUrl(piece.getPieceId()), 
        //             piece.getKeywords(), 
        //             piece.getWhos()));
        // }
        // return pieceResponses;
        return pieces;
    }

    public Piece updatePiece(Long pieceId, Piece piece) throws NotFoundException {
        Optional<Piece> oPiece = pieceRepository.findById(pieceId);
        if(!oPiece.isPresent()) throw new NotFoundException(String.valueOf(pieceId) + " is not found");
        oPiece.get().withTitle(piece.getTitle())
            .withContent(piece.getContent())
            .withLatitude(piece.getLatitude())
            .withLongitude(piece.getLongitude());
        pieceRepository.save(oPiece.get());
        return oPiece.get();
    }

    public Piece savePiece(PieceRequest pieceRequest, String userId){
        Optional<User> user  = userRepository.findById(userId);
        if(!user.isPresent()) throw new NotFoundException("wrong user id");
        Piece piece = new Piece()
            .withUser(user.get())
            .withTitle(pieceRequest.getTitle())
            .withDate(pieceRequest.getDate())
            .withContent(pieceRequest.getContent())
            .withLatitude(pieceRequest.getLatitude())
            .withLongitude(pieceRequest.getLongitude())
            .withPlaceUrl(pieceRequest.getPlaceUrl())
            .withAddress(pieceRequest.getAddress())
            .withAddressDetail(pieceRequest.getAddressDetail());
        logger.info(pieceRequest.getAddress() + " " + piece.getAddress());
        List<Keyword> keywords = new ArrayList<Keyword>();
        logger.info("pservice");
        try{
            logger.info(pieceRequest.getKeywords());
            for(Long keyword_id : Arrays.stream(pieceRequest.getKeywords().split(",")).mapToLong(Long::parseLong).toArray()){
                Optional<Keyword> keyword = keywordRepository.findById(keyword_id);
                if(keyword.isPresent()){
                    keywords.add(keyword.get());
                }
            }
            piece.setKeywords(keywords);
            logger.info("not keyword");
            List<WhoKeyword> whos = new ArrayList<WhoKeyword>();
            for(Long who_id : Arrays.stream(pieceRequest.getWhos().split(",")).mapToLong(Long::parseLong).toArray()){
                Optional<WhoKeyword> who = whoKeywordRepository.findById(who_id);
                if(who.isPresent()){
                    whos.add(who.get());
                }
            }
            piece.setWhos(whos);
            logger.info("not whos");
        } catch(Exception e) {
            
        }
        
        pieceRepository.save(piece.withCreatedAt(new Date()));
        logger.info("into file");
        for(MultipartFile file : pieceRequest.getImages()){
            if(file.getOriginalFilename().length() != 0)
                imageService.uploadImage(file, piece.getPieceId());
        }
        logger.info("file");
        return piece;
    }
}
