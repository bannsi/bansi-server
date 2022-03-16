package com.gotgam.bansi.service;

import java.util.ArrayList;
import java.util.List;

import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
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
    public Image saveImage(ImageRequest imageDTO){
        return imageRepository.save(new Image(imageDTO.getImage(), imageDTO.getThumbnail()));
    }

    @Override
    public List<Image> saveAllImages(List<ImageRequest> imageDTOs){
        List<Image> images = new ArrayList<>();
        for(ImageRequest imageDTO : imageDTOs){
            images.add(new Image(imageDTO.getImage(), imageDTO.getThumbnail()));
        }
        return Streamable.of(imageRepository.saveAll(images)).toList();
    }
}
