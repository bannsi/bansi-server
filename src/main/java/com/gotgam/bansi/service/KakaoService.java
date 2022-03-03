package com.gotgam.bansi.service;

import java.util.HashMap;

public interface KakaoService {
    public String getAccessToken(String authCode);
    public HashMap<String, Object> getUserInfo(String accessToken);
    public String getAuthCode();
}
