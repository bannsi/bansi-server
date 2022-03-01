package com.gotgam.bansi.service;

import java.util.List;

import com.gotgam.bansi.model.Image;

public interface ImageService {
    public Image saveImage(String encoded);
    public List<Image> saveAllImages(List<String> encodeds);
}
