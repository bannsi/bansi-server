package com.gotgam.bansi.controller;

import java.util.HashMap;

import com.gotgam.bansi.DTO.AccountDTO.UserResponse;
import com.gotgam.bansi.DTO.ResponseDTO;
import com.gotgam.bansi.model.Piece;
import com.gotgam.bansi.model.User;
import com.gotgam.bansi.service.KakaoService;
import com.gotgam.bansi.service.PieceService;
import com.gotgam.bansi.service.UserService;
import com.gotgam.bansi.util.JwtUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;




@Slf4j
@RestController
@RequestMapping(value = "/accounts/v1")
public class AccountController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final PieceService pieceService;
    private final JwtUtil jwtUtil;

    public AccountController(KakaoService kakaoService, UserService userService, PieceService pieceService, JwtUtil jwtUtil){
        this.kakaoService = kakaoService;
        this.userService = userService;
        this.pieceService = pieceService;
        this.jwtUtil = jwtUtil;
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
    
    @RequestMapping(value = "/kakao/authCode", method = RequestMethod.GET)
    public String kakaoAuthCode(){
        String authCode = kakaoService.getAuthCode();
        return authCode;
    }

    @RequestMapping(value = "/kakao/login/", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> kakaoLogin(String code, Model model) throws Exception {
        log.info("Authorization Code = " + code);
        String accessToken = kakaoService.getAccessToken(code);
        log.info("Access Token: " + accessToken);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
        log.info("nickname: " + String.valueOf(userInfo.get("nickname")));

        User user = userService.CreateUser(
            String.valueOf(userInfo.get("userId")), 
            String.valueOf(userInfo.get("nickname")),
            String.valueOf(userInfo.get("encodedImage")));
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok().body(new ResponseDTO("user created", token));
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getMeInfo(@RequestHeader HttpHeaders headers) throws Exception {
        String token = headers.getFirst("Authorization").substring(7);
        String kakaoId = jwtUtil.getUsernameFromToken(token);
        User user = userService.getUserFromId(kakaoId);
        return ResponseEntity.ok().body(new UserResponse("S00", "get user info", user));
    }

    @RequestMapping(value="/me/", method=RequestMethod.PUT)
    public ResponseEntity<UserResponse> requestMethodName(@RequestHeader HttpHeaders headers, @RequestBody User newUser) throws Exception{
        String token = headers.getFirst("Authorization").substring(7);
        String kakaoId = jwtUtil.getUsernameFromToken(token);
        User user = userService.updateUser(kakaoId, newUser);
        return ResponseEntity.ok().body(new UserResponse("S00","user nickcname chnaged", user));
    }
    
    @RequestMapping(value="/piece/{pieceId}", method=RequestMethod.GET)
    public ResponseEntity<UserResponse> findUserBypeice(@PathVariable Long pieceId) {
        Piece piece = pieceService.getPieceByPieceId(pieceId);
        return ResponseEntity.ok().body(new UserResponse("S00", "find user by pieceId", piece.getUser()));
    }
}   
