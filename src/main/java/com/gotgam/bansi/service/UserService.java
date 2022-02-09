package com.gotgam.bansi.service;

import java.util.Optional;

import com.gotgam.bansi.model.User;
import com.gotgam.bansi.respository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User CreateUser(String kakaoId, String nickname){
        Optional<User> opUser = userRepository.findByKakaoId(kakaoId);
        User user = new User();
        if(!opUser.isPresent()){
            user.withKakaoId(kakaoId).withNickname(nickname);
            userRepository.save(user);
        } else {
            user = opUser.get();
        }
        return user;
    }

    public User getUserFromId(String kakaoId) throws Exception{
        Optional<User> opUser = userRepository.findByKakaoId(kakaoId);
        if(!opUser.isPresent()){
            throw new Exception();
        }
        return opUser.get();
    }
    
    public User updateUser(String kakaoId, User newUser) throws Exception{
        User user = getUserFromId(kakaoId);
        user.setNickname(newUser.getNickname());
        userRepository.save(user);
        return user;
    }
}