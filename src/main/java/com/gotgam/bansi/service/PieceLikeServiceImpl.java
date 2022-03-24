package com.gotgam.bansi.service;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceLike;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.PieceLikeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PieceLikeServiceImpl implements PieceLikeService {
    private final PieceLikeRepository pieceLikeRepository;

    @Override
    public Long createPieceLike(Piece piece, User user){
        PieceLike pieceLike = new PieceLike(user, piece);
        pieceLikeRepository.save(pieceLike);
        return pieceLikeRepository.countByPiece(piece);
    }

    @Override
    public Long deletePieceLike(Piece piece, User user){
        pieceLikeRepository.deleteByPieceAndUser(piece, user);
        return pieceLikeRepository.countByPiece(piece);
    }

    @Override
    public Long countPieceLike(Piece piece){
        return pieceLikeRepository.countByPiece(piece);
    }
}
