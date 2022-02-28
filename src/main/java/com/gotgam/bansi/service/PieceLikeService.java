package com.gotgam.bansi.service;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceLike;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.PieceLikeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PieceLikeService {
    private final PieceLikeRepository pieceLikeRepository;

    public PieceLikeService(PieceLikeRepository pieceLikeRepository){
        this.pieceLikeRepository = pieceLikeRepository;
    }

    public Long createPieceLike(Piece piece, User user){
        PieceLike pieceLike = new PieceLike(user, piece);
        pieceLikeRepository.save(pieceLike);
        return pieceLikeRepository.countByPiece(piece);
    }

    public Long deletePieceLike(Piece piece, User user){
        pieceLikeRepository.deleteByPieceAndUser(piece, user);
        return pieceLikeRepository.countByPiece(piece);
    }
}
