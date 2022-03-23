package com.gotgam.bansi.service;

import java.util.Optional;

import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.ImageRepository;
import com.gotgam.bansi.respository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public User CreateUser(String kakaoId, String nickname, String encodedImage){
        Optional<User> opUser = userRepository.findByKakaoId(kakaoId);
        User user = new User();
        if(!opUser.isPresent()){
            user.withKakaoId(kakaoId).withNickname(nickname);
            userRepository.save(user);
        } else {
            user = opUser.get();
        }
        user.setImage(encodedImage);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserFromId(String kakaoId){
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new NotFoundException("wrong user id"));
        return user;
    }

    @Override
    public User updateUser(String kakaoId, User newUser) {
        User user = getUserFromId(kakaoId);
        user.setNickname(newUser.getNickname());
        return user;
    }
}