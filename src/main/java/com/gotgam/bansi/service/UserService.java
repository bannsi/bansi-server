package com.gotgam.bansi.service;

import com.gotgam.bansi.model.User;

public interface UserService {
    User CreateUser(String kakaoId, String nickname, String encodedImage);   
    User getUserFromId(String kakaoId);
    User updateUser(String kakaoId, User newUser);
}
