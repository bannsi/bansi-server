package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.List;

import com.gotgam.bansi.model.Image;
import com.gotgam.bansi.respository.ImageRepository;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    
    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(String encoded){
        return imageRepository.save(new Image(encoded));
    }

    @Override
    public List<Image> saveAllImages(List<String> encodeds){
        List<Image> images = new ArrayList<>();
        for(String encoded : encodeds){
            images.add(new Image(encoded));
        }
        return Streamable.of(imageRepository.saveAll(images)).toList();
    }
}
