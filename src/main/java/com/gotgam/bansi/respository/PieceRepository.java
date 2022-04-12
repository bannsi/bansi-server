package com.gotgam.bansi.respository;

import java.util.List;
import java.util.Set;

import com.gotgam.bansi.model.Keyword;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.PlaceKeyword;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.model.WhoKeyword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece,Long>{
    List<Piece> findByUser(User user);
    List<Piece> findAll();
    
    List<Piece> findByKeywords_Id(Long keywordId);
    List<Piece> findByOpKeywords_id(Long opKeywordId);
    List<Piece> findByWhos_Id(Long whoId);
    List<Piece> findByPlace_Name(String placeName);

    List<Piece> findByKeywords_IdAndWhos_Id(Long keywordId, Long whoId);
    List<Piece> findByWhos_IdAndPlace_Name(Long whoId, String placeName);
    List<Piece> findByKeywords_IdAndPlace_Name(Long keywordId, String placeName);

    List<Piece> findByKeywordsAndWhosAndPlace(Keyword keyword, WhoKeyword whoKeyword, PlaceKeyword placeKeyword);
    
    Set<Piece> findAllByKeywords_IdInAndWhos_IdInAndPlace_NameIn(List<Long> keywords, List<Long> whoIds, List<String> placeNames);

    @Query(value = "SELECT coalesce(max(pieces.piece_id), 0) FROM pieces", nativeQuery = true)
    Long getMaxId();
    @Query(value = "SELECT pieces.piece_id FROM pieces ORDER BY pieces.created_at DESC LIMIT 100", nativeQuery = true)
    List<Long> findIdAll();
    // @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true", nativeQuery = true)
    // List<ThumbNail> findThumbNails();
    // @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true AND p.user_kakao_id = ?1", nativeQuery = true)
    // List<ThumbNail> findThumbNailByUserId(String userId);
    // @Query(value = "SELECT p.piece_id AS pieceId, p.user_kakao_id AS userId, img.encoded AS encoded FROM pieces p LEFT JOIN image img ON p.piece_id = img.piece_id WHERE img.thumbnail = true AND p.piece_id in :ids", nativeQuery = true)
    // List<ThumbNail> findAllThumbNailById(@Param("ids") List<Long> ids);
}
