package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PieceImage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceImageRepository extends CrudRepository<PieceImage, Long>{
        List<PieceImage> findByPiece(Piece piece);
}
