package com.gotgam.bansi.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class KakaoServiceImpl implements KakaoService {
    private static final String APIKEY = "057a07ccce2a54f042f9b62e61d4457f";

    @Override
    public String getAccessToken(String authCode) {
        String accessToken = new String();
        // String refreshToken = new String();
        String tokenURL = "https://kauth.kakao.com/oauth/token";
        log.info("get access token");
        try{
            URL url = new URL(tokenURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("grant_type=authorization_code");
            stringBuilder.append("&client_id="+APIKEY);
            stringBuilder.append("&redirect_uri=http://localhost:8080/accounts/v1/kakao/login/");
            stringBuilder.append("&code=" + authCode);
            bufferedWriter.write(stringBuilder.toString());           
            bufferedWriter.flush();

            if (conn.getResponseCode() != 200) {
                log.info("get kakao access token failed: " + String.valueOf(conn.getResponseCode()));
                log.info(conn.getResponseMessage());
                throw new IOException();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = new String();
            String result = new String();

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            log.info(result);
            JsonNode jsonNode = new ObjectMapper().readTree(result);

            accessToken = jsonNode.get("access_token").asText();
            bufferedReader.close();
            bufferedWriter.close();
        } catch(IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return accessToken;
    }

    @Override
    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String userInfoURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(userInfoURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseStatus = conn.getResponseCode();
            if(responseStatus != 200){
                throw new IOException();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = new String();
            String result = new String();
            
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            log.info("result: " + result);
            
            JsonNode jsonNode = new ObjectMapper().readTree(result);
            String kakaoUserId = jsonNode.get("id").asText();
            JsonNode profile = jsonNode.get("kakao_account").get("profile");
            String nickname = profile.get("nickname").asText();
            String imageUrl = profile.get("profile_image_url").asText();

            log.info("id: " + kakaoUserId + ", nickname: " + nickname);
            userInfo.put("accessToken", accessToken);
            userInfo.put("userId", kakaoUserId);
            userInfo.put("nickname", nickname);
            userInfo.put("encodedImage", imageUrl);
        } catch(IOException e){
            e.printStackTrace();;
        }
        return userInfo;
    }

    @Override
    public String getAuthCode(){
        String authCodeURL = "https://kauth.kaako.com/oauth/authorize";
        String authCode = new String();
        try{
            URL url = new URL(authCodeURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("client_id="+APIKEY);
            stringBuilder.append("&redirect-uri=http://localhost:8080/account/kakaoLogin");
            stringBuilder.append("&response_type=code");

            if(conn.getResponseCode() != 200){
                log.debug("get kakao authorization code failed: " + String.valueOf(conn.getResponseCode()));
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = new String();
            String result = new String();

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            JsonNode jsonNode = new ObjectMapper().readTree(result);

            authCode = jsonNode.get("code").asText();

            bufferedReader.close();
            bufferedWriter.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return authCode;
    }
}
