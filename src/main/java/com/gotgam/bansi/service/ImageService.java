package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.DTO.ImageDTO.ImageRequest;
import com.gotgam.bansi.model.Image;

public interface ImageService {
    public Image saveImage(ImageRequest imageDTO);
    public List<Image> saveAllImages(List<ImageRequest> imageRequests);
}
