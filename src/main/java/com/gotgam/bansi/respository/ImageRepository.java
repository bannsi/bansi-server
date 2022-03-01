package com.gotgam.bansi.respository;

import com.gotgam.bansi.model.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    // List<Image> saveAll(Iterable<Image> images);
}
