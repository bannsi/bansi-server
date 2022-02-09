package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

import org.springframework.data.repository.CrudRepository;

public interface PieceRepository extends CrudRepository<Piece,Long>{
    List<Piece> findByUser(User user);
}
