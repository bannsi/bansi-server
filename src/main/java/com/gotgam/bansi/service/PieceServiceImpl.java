package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Image;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.OptionalKeywordRepository;
import com.gotgam.bansi.respository.PieceRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {
    private final PieceRepository pieceRepository;
    private final KeywordRepository keywordRepository;
    private final WhoKeywordRepository whoKeywordRepository;
    private final OptionalKeywordRepository opKeywordRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final PlaceKeywordService placeKeywordService;
    private final PieceLikeService likeService;
    private final CommentService commentService;
    private final ThumbNailService thumbNailService;

    private final Integer RANDOM_PIECES = 6;

    @Override
    public Piece getPieceByPieceId(Long pieceId) {
        Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("wrong pieceId"));
        return piece;
    }

    @Override
    public List<Piece> findPieceByUserId(String userId){
        User user = userService.getUserFromId(userId);
        List<Piece> pieces = pieceRepository.findByUser(user);
        return pieces;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Piece updatePiece(Long pieceId, Piece piece) {
        Piece oPiece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("wrong pieceId"));
        oPiece.withContent(piece.getContent())
              .withLatitude(piece.getLatitude())
              .withLongitude(piece.getLongitude());
        return oPiece;
    }

    // TODO: user는 userservice에서 가져와서 해주자
    // 읽기는 다른 서비스에서 가져와서 해주는게 좋은것 같음
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Piece savePiece(PieceRequest pieceRequest, String userId){
        User user = userService.getUserFromId(userId);

        List<Keyword> keywords = keywordRepository.findAllById(pieceRequest.getKeywords());
        List<OptionalKeyword> opKeywords = new ArrayList<>();

        if(pieceRequest.getOptionalKeywords().size() != 0){
            log.info(pieceRequest.getOptionalKeywords().toString());
            opKeywords = opKeywordRepository.findAllById(pieceRequest.getOptionalKeywords());
        }
        List<WhoKeyword> whos = whoKeywordRepository.findAllById(pieceRequest.getWhos());
        List<ImageRequest> imageReqs = new ArrayList<>();
        String thumbnailImage = null;
        for(ImageRequest imageReq : pieceRequest.getImages()){
            if(imageReq.getThumbnail()) thumbnailImage = imageReq.getImage();
            else imageReqs.add(imageReq);
        }
        List<Image> images = imageService.saveAllImages(imageReqs);
        Piece piece = new Piece()
            .withUser(user)
            .withDate(pieceRequest.getDate())
            .withContent(pieceRequest.getContent())
            .withLatitude(pieceRequest.getLatitude())
            .withLongitude(pieceRequest.getLongitude())
            .withPlaceUrl(pieceRequest.getPlaceUrl())
            .withAddress(pieceRequest.getAddress())
            .withAddressDetail(pieceRequest.getAddressDetail());

        // piece.setPlace(placeKeywordService.getOrCreate(pieceRequest.getPlace()));
        // piece.setKeywords(keywords);
        // piece.setOpKeywords(opKeywords);
        // piece.setWhos(whos);
        piece.setImages(images);
        pieceRepository.save(piece.withCreatedAt(new Date()));
        thumbNailService.createThumbNail(
            piece, 
            user, 
            thumbnailImage, 
            Set.copyOf(keywords), 
            Set.copyOf(opKeywords), 
            Set.copyOf(whos), 
            placeKeywordService.getOrCreate(pieceRequest.getPlace()));
        return piece;
    }

    @Override
    public List<ThumbNailDTO> findRandomPieces(){
        List<Long> candIds = pieceRepository.findIdAll();
        Random rand = new Random(System.currentTimeMillis());
        List<Long> ids = new ArrayList<>();
        for(int i = 0; i < RANDOM_PIECES; i++){
            ids.add(candIds.get(rand.nextInt(candIds.size())));
        }
        return pieceRepository.findAllById(ids).stream().map(
            piece -> new ThumbNailDTO(
                piece.getPieceId(), 
                piece.getUser().getKakaoId(), 
                piece.getImages().stream().filter(
                    image -> image.getThumbnail().equals(Boolean.TRUE)).findAny().get().getEncoded()))
            .collect(Collectors.toList());
    }

    @Override
    public void deletePiece(Long pieceId){
        pieceRepository.deleteById(pieceId);
    }

    @Override
    public List<Piece> findByPlace(String placeName){
        List<Piece> pieces = pieceRepository.findByPlace_Name(placeName);
        return pieces;
    }

    @Override
    public List<Piece> findByKeyword(Long keywordId){
        List<Piece> pieces = pieceRepository.findByKeywords_Id(keywordId);
        return pieces;
    }

    @Override
    public List<Piece> findByWho(Long whoId){
        List<Piece> pieces = pieceRepository.findByWhos_Id(whoId);
        return pieces;
    }

    @Override
    public Long likePiece(Long pieceId, String userId){
        User user = userService.getUserFromId(userId);
        Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("잘못된 조각 아이디"));
        Long likeCount = likeService.createPieceLike(piece, user);
        return likeCount;
    }

    @Override
    public Long dislikePiece(Long pieceId, String userId){
        User user = userService.getUserFromId(userId);
        Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("잘못된 조각 아이디"));
        Long likeCount = likeService.deletePieceLike(piece, user);
        return likeCount;
    }

    @Override
    public List<ThumbNailDTO> filterByKeywords(List<Long> whoIds, List<Long> keywordIds, List<String> placeNames){
        if(whoIds.size() == 0) whoIds = whoKeywordRepository.findAll().stream().map(WhoKeyword::getId).collect(Collectors.toList());
        if(keywordIds.size() == 0) keywordIds = keywordRepository.findAll().stream().map(Keyword::getId).collect(Collectors.toList());
        if(placeNames.size() == 0) placeNames = placeKeywordService.findAll().stream().map(PlaceKeyword::getName).collect(Collectors.toList());
        Set<Piece> pieces = pieceRepository.findAllByKeywords_IdInAndWhos_IdInAndPlace_NameIn(keywordIds,whoIds, placeNames);
        return pieces.stream().map(
            piece -> new ThumbNailDTO(
                piece.getPieceId(), 
                piece.getUser().getKakaoId(), 
                piece.getImages().stream().filter(
                    image -> image.getThumbnail().equals(Boolean.TRUE)).findAny().get().getEncoded()))
            .collect(Collectors.toList());
    }
}
