package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceLike;
import com.gotgam.bansi.model.User;

import org.springframework.data.repository.CrudRepository;

public interface PieceLikeRepository extends CrudRepository<PieceLike, Long>{
    Long countByPiece(Piece piece);
    void deleteByPieceAndUser(Piece piece, User user);
}
