package com.gotgam.bansi.respository;

import java.util.Optional;

import com.gotgam.bansi.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String>{
    Optional<User> findByKakaoId(String kakaoId);
}
