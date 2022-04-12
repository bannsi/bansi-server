package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.ThumbNail;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;
import com.gotgam.bansi.respository.KeywordRepository;
import com.gotgam.bansi.respository.ThumbNailRepository;
import com.gotgam.bansi.respository.WhoKeywordRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ThumbNailServiceImpl implements ThumbNailService {
    private final ThumbNailRepository thumbNailRepository;
    private final WhoKeywordRepository whoKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final PlaceKeywordService placeKeywordService;
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
    public Page<ThumbNailDTO> findThumbNailsByUserId(String userId, PageRequest pageRequest){
        Page<ThumbNail> thumbNails =  thumbNailRepository.findAllByUser_KakaoId(userId, pageRequest);
        Page<ThumbNailDTO> pages = thumbNails.map(new Function<ThumbNail, ThumbNailDTO>(){
            @Override
            public ThumbNailDTO apply(ThumbNail thumbNail){
                ThumbNailDTO thumbNailDTO = new ThumbNailDTO(thumbNail);
                return thumbNailDTO;
            }
        });
        return pages;
    }

    @Override
    public ThumbNailDTO createThumbNail(Piece piece, User user, String encoded, Set<Keyword> keywords, Set<OptionalKeyword> opKeywords, Set<WhoKeyword> whoKeywords, PlaceKeyword placeKeyword){
        ThumbNail thumbNail = new ThumbNail(piece, user, encoded, keywords, opKeywords, whoKeywords, placeKeyword);
        thumbNail = thumbNailRepository.save(thumbNail);
        return new ThumbNailDTO(thumbNail.getPiece().getPieceId(), thumbNail.getUser().getKakaoId(), thumbNail.getEncoded());
    }

    @Override
    public Page<ThumbNailDTO> findByKeywords(List<Long> whoIds, List<Long> keywordIds, List<String> placeNames, PageRequest pageRequest){
        
        if(keywordIds.size() == 0) keywordIds = keywordRepository.findAll().stream().map(Keyword::getId).collect(Collectors.toList());
        if(placeNames.size() == 0) placeNames = placeKeywordService.findAll().stream().map(PlaceKeyword::getName).collect(Collectors.toList());
        if(whoIds.size() == 0) whoIds = whoKeywordRepository.findAll().stream().map(WhoKeyword::getId).collect(Collectors.toList());
        
        Page<ThumbNail> thumbNails = thumbNailRepository.findAllByKeywords_IdInAndWhoKeywords_IdInAndPlaceKeyword_NameIn(keywordIds, whoIds, placeNames, pageRequest);
        Page<ThumbNailDTO> pages = thumbNails.map(new Function<ThumbNail, ThumbNailDTO>(){
            @Override
            public ThumbNailDTO apply(ThumbNail thumbNail){
                ThumbNailDTO thumbNailDTO = new ThumbNailDTO(thumbNail);
                return thumbNailDTO;
            }
        });
        return pages;
    }

    @Override
    public ThumbNail getByPiece_Id(Long pieceId){
        return getByPiece_Id(pieceId);
    }
}
