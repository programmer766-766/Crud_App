package com.crud.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

    private SecretKey getSignedKey(){
        String SECRET_KEY = "QtusISjQOZF8IiTBiD5R97mZLHMpl4nQIdP88cZ6aMl";
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignedKey())
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    private Date extractExpiration(String token){
        return getClaims(token).getExpiration();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = getClaims(token);

        return claims.getSubject().equals(userDetails.getUsername())
                && claims.getExpiration().after(new Date());
    }
}
