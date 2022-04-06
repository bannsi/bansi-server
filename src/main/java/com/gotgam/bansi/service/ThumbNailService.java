package com.gotgam.bansi.service;

import java.util.List;
import java.util.Set;

import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.OptionalKeyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;

public interface ThumbNailService {
    List<ThumbNailDTO> findRandomThumbNails();
    List<ThumbNailDTO> findThumbNailsByUserId(String userId);
    List<ThumbNailDTO> findByKeywords(List<Long> whoIds, List<Long> keywordIds, List<String> placeNames);

    ThumbNailDTO createThumbNail(Piece piece, User user, String encoded, Set<Keyword> keywords, Set<OptionalKeyword> opKeywords, Set<WhoKeyword> whoKeywords, PlaceKeyword placeKeyword);
}
