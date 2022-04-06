package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.ThumbNail;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.ThumbNailRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ThumbNailServiceImpl implements ThumbNailService {
    private final ThumbNailRepository thumbNailRepository;
    private final Integer RANDOM_PIECES = 6;

    @Override
    public List<ThumbNailDTO> findRandomThumbNails(){
        List<Long> candIds = thumbNailRepository.findIdAll();
        Random rand = new Random(System.currentTimeMillis());
        List<Long> ids = new ArrayList<>();
        for(int i = 0; i < RANDOM_PIECES; i++){
            ids.add(candIds.get(rand.nextInt(candIds.size())));
        }
        return thumbNailRepository.findAllById(ids).stream().map(tn -> new ThumbNailDTO(tn.getPiece().getPieceId(), tn.getUser().getKakaoId(), tn.getEncoded())).collect(Collectors.toList());
    }

    @Override
    public List<ThumbNailDTO> findThumbNailsByUserId(String userId){
        return thumbNailRepository.findAllByUser_KakaoId(userId).stream().map(tn -> new ThumbNailDTO(tn.getPiece().getPieceId(), tn.getUser().getKakaoId(), tn.getEncoded())).collect(Collectors.toList());
    }

    @Override
    public ThumbNailDTO createThumbNail(Piece piece, User user, String encoded, Set<Keyword> keywords, Set<OptionalKeyword> opKeywords, Set<WhoKeyword> whoKeywords, PlaceKeyword placeKeyword){
        ThumbNail thumbNail = new ThumbNail(piece, user, encoded, keywords, opKeywords, whoKeywords, placeKeyword);
        thumbNail = thumbNailRepository.save(thumbNail);
        return new ThumbNailDTO(thumbNail.getPiece().getPieceId(), thumbNail.getUser().getKakaoId(), thumbNail.getEncoded());
    }
}
