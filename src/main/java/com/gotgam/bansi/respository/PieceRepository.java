package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.DAO.ThumbNail;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PieceRepository extends JpaRepository<Piece,Long>{
    List<Piece> findByUser(User user);
    
    List<Piece> findAllByKeywords_Id(Long keywordId);
    List<Piece> findAllByOpKeywords_id(Long opKeywordId);
    List<Piece> findAllByWhos_Id(Long whoId);
    List<Piece> findAllByPlace_Name(String placeName);
    @Query(value = "SELECT coalesce(max(pieces.piece_id), 0) FROM pieces", nativeQuery = true)
    Long getMaxId();
    @Query(value = "SELECT pieces.piece_id FROM pieces ORDER BY pieces.created_at DESC LIMIT 100", nativeQuery = true)
    List<Long> findIdAll();
    @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true", nativeQuery = true)
    List<ThumbNail> findThumbNails();
    @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true AND p.user_kakao_id = ?1", nativeQuery = true)
    List<ThumbNail> findThumbNailByUserId(String userId);
    @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true AND p.piece_id in :ids", nativeQuery = true)
    List<ThumbNail> findAllThumbNailById(@Param("ids") List<Long> ids);
}
