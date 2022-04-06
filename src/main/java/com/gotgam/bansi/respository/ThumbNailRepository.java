package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.ThumbNail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThumbNailRepository extends JpaRepository<ThumbNail, Long> {
    List<ThumbNail> findAllById(Iterable<Long> ids);
    List<ThumbNail> findAllByUser_KakaoId(String userId);

    @Query(value = "SELECT thumb_nail.id FROM thumb_nail LIMIT 100", nativeQuery = true)
    List<Long> findIdAll();
}
