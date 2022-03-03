package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PieceRepository extends JpaRepository<Piece,Long>{
    List<Piece> findByUser(User user);
    List<Piece> findAllById(Iterable<Long> ids);
    List<Piece> findAllByKeywords_Id(Long keywordId);
    List<Piece> findAllByOpKeywords_id(Long opKeywordId);
    List<Piece> findAllByWhos_Id(Long whoId);
    @Query(value = "SELECT coalesce(max(pieces.piece_id), 0) FROM pieces", nativeQuery = true)
    Long getMaxId();
    @Query(value = "SELECT pieces.piece_id FROM pieces ORDER BY pieces.created_at DESC LIMIT 100", nativeQuery = true)
    List<Long> findIdAll();
}
