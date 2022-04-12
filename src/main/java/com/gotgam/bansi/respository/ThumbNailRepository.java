package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.ThumbNail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThumbNailRepository extends JpaRepository<ThumbNail, Long> {
    ThumbNail getByPiece_PieceId(Long pieceId);
    List<ThumbNail> findAllById(Iterable<Long> ids);
    List<ThumbNail> findAllByUser_KakaoId(String userId);
    List<ThumbNail> findAllByKeywords_IdInAndWhoKeywords_IdInAndPlaceKeyword_NameIn(List<Long> keywords, List<Long> whoIds, List<String> placeNames);
    
    @Query(value = "SELECT thumb_nail.id FROM thumb_nail LIMIT 100", nativeQuery = true)
    List<Long> findIdAll();

    Page<ThumbNail> findAllByUser_KakaoId(String userId, PageRequest pageRequest);
    Page<ThumbNail> findAllByKeywords_IdInAndWhoKeywords_IdInAndPlaceKeyword_NameIn(List<Long> keywords, List<Long> whoIds, List<String> placeNames, PageRequest pageRequest);
}
