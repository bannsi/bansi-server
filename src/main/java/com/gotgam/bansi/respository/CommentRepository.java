package com.gotgam.bansi.respository;

import java.util.List;
import java.util.Optional;

import com.gotgam.bansi.model.Comment;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
    List<Comment> findAllByPiece(Piece piece);
    List<Comment> findAllByPiece_PieceId(Long pieceId);
    
    Page<Comment> findAllByPiece(Piece piece, Pageable pageable);
}
