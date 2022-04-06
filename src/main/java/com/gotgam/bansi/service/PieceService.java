package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.DTO.ThumbnailDTO.ThumbNailDTO;
import com.gotgam.bansi.model.Piece;

public interface PieceService {
    Piece getPieceByPieceId(Long pieceId);
    List<Piece> findPieceByUserId(String userId);
    Piece updatePiece(Long pieceId, Piece piece);
    Piece savePiece(PieceRequest pieceRequest, String userId);
    List<ThumbNailDTO> findRandomPieces();
    // 필터 : 지역, 키워드, who
    List<Piece> findByPlace(String placeName);
    List<Piece> findByKeyword(Long keywordId);
    List<Piece> findByWho(Long whoId);
    List<ThumbNailDTO> filterByKeywords(List<Long> whoIds, List<Long> keywordIds, List<String> placeNames);
    // List<PieceThumbnail> 
    void deletePiece(Long pieceId);

    Long likePiece(Long pieceId, String userId);
    Long dislikePiece(Long pieceId, String userId);
}
