package com.gotgam.bansi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.gotgam.bansi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final long JWT_TOKEN_VALIDITY = 180 * 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;
    
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getKakaoId());
    }
    public boolean validateToken(String token, User user){
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getKakaoId()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromTokenStr(String tokenStr) throws NotFoundException {
        if(tokenStr == null || tokenStr.length() < 7) throw new NotFoundException("wrong token");
        String token = tokenStr.substring(7);
        return getUsernameFromToken(token);
    }
}
