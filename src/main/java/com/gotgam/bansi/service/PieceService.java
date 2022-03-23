package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.ImageDTO.PieceThumbnail;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

public interface PieceService {
    Piece getPieceByPieceId(Long pieceId);
    List<Piece> findPieceByUserId(String userId);
    Piece updatePiece(Long pieceId, Piece piece);
    Piece savePiece(PieceRequest pieceRequest, String userId);
    List<Piece> findRandomPieces();
    // 필터 : 지역, 키워드, who
    List<Piece> findByPlace(String placeName);
    List<Piece> findByKeyword(Long keywordId);
    List<Piece> findByWho(Long whoId);
    // List<PieceThumbnail> 
    void deletePiece(Long pieceId);
    List<PieceThumbnail> findThumbnails(User user);
    List<PieceThumbnail> findByKeywordId(Long keywordId);
}
