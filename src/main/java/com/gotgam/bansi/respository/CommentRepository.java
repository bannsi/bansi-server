package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.Comment;
import com.gotgam.bansi.model.Piece;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByPiece(Piece piece);
}
