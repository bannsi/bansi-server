package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.kms.model.NotFoundException;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.OptionalKeywordRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.UserRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class PieceService {
    @Autowired
    private PieceRepository pieceRepository;
    
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private WhoKeywordRepository whoKeywordRepository;

    @Autowired
    private OptionalKeywordRepository opKeywordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    public Piece getPieceByPieceId(Long pieceId) {
        Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("wrong pieceId"));
        return piece;
    }

    public List<Piece> findPieceByUserId(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new org.webjars.NotFoundException("wrong token"));
        List<Piece> pieces = pieceRepository.findByUser(user);
        return pieces;
    }

    public Piece updatePiece(Long pieceId, Piece piece) {
        Piece oPiece = pieceRepository.findById(pieceId).orElseThrow(() -> new org.webjars.NotFoundException("wrong pieceId"));
        oPiece.withContent(piece.getContent())
              .withLatitude(piece.getLatitude())
              .withLongitude(piece.getLongitude());
        pieceRepository.save(oPiece);
        return oPiece;
    }
    // TODO: user는 userservice에서 가져와서 해주자
    // 읽기는 다른 서비스에서 가져와서 해주는게 좋은것 같음
    public Piece savePiece(PieceRequest pieceRequest, String userId){
        User user  = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("wrong token"));
        List<Keyword> keywords = keywordRepository.findAllById(pieceRequest.getKeywords());
        List<OptionalKeyword> opKeywords = new ArrayList<>();
        if(pieceRequest.getOptionalKeywords().size() != 0){
            log.info(pieceRequest.getOptionalKeywords().toString());
            opKeywords = opKeywordRepository.findAllById(pieceRequest.getOptionalKeywords());
        }
        List<WhoKeyword> whos = whoKeywordRepository.findAllById(pieceRequest.getWhos());
        Piece piece = new Piece()
            .withUser(user)
            .withDate(pieceRequest.getDate())
            .withContent(pieceRequest.getContent())
            .withLatitude(pieceRequest.getLatitude())
            .withLongitude(pieceRequest.getLongitude())
            .withPlaceUrl(pieceRequest.getPlaceUrl())
            .withAddress(pieceRequest.getAddress())
            .withAddressDetail(pieceRequest.getAddressDetail());
        piece.setKeywords(keywords);
        piece.setOpKeywords(opKeywords);
        piece.setWhos(whos);

        log.info(pieceRequest.getAddress() + " " + piece.getAddress());

        pieceRepository.save(piece.withCreatedAt(new Date()));

        for(MultipartFile file : pieceRequest.getImages()){
            if(file.getOriginalFilename().length() != 0)
                imageService.uploadImage(file, piece.getPieceId());
        }
        return piece;
    }
}
