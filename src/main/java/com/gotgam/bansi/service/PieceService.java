package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DAO.PieceThumnail;
import com.gotgam.bansi.DTO.PieceDTO.PieceRequest;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

public interface PieceService {
    Piece getPieceByPieceId(Long pieceId);
    List<Piece> findPieceByUserId(String userId);
    Piece updatePiece(Long pieceId, Piece piece);
    Piece savePiece(PieceRequest pieceRequest, String userId);
    List<Piece> findRandomPieces();
    void deletePiece(Long pieceId);
    List<PieceThumnail> findThumnails(User user);
}
