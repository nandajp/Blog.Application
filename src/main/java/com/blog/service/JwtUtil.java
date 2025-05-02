package com.blog.service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
    private static  final long EXPIRATION_TIME = 86400000;
    private static final String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkey";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    public String generateToken(String username){
        var token =  Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
        System.out.println("Generated Token: " + token);
        return token;
    }
    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }
    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }
    public Claims parseClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
}
