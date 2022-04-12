package com.gotgam.bansi.service;

import java.util.List;
import java.util.Set;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.ThumbNail;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ThumbNailService {
    ThumbNail getByPiece_Id(Long pieceId);
    List<ThumbNailDTO> findRandomThumbNails();
    Page<ThumbNailDTO> findThumbNailsByUserId(String userId, PageRequest pageRequest);
    Page<ThumbNailDTO> findByKeywords(List<Long> whoIds, List<Long> keywordIds, List<String> placeNames, PageRequest pageRequest);

    ThumbNailDTO createThumbNail(Piece piece, User user, String encoded, Set<Keyword> keywords, Set<OptionalKeyword> opKeywords, Set<WhoKeyword> whoKeywords, PlaceKeyword placeKeyword);
}
