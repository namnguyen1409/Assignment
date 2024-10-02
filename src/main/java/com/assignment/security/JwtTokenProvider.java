package com.assignment.security;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.config.PropertiesConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    private PropertiesConfig prop;

    private String JWT_SECRET;
    private int JWT_EXPIRATION;

    @PostConstruct
    public void init() {
        this.JWT_SECRET = prop.JWT_SECRET;
        this.JWT_EXPIRATION = prop.JWT_EXPIRATION;
    }
    
    
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION*1000L);
        System.out.println("JWT_EXPIRATION: " + expiryDate.toString());
        SecretKey key = generateKey();
        return Jwts.builder()
                .subject(Long.toString(userDetails.getUser().getId()))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        var claims = getClaimsFromToken(token);
        return Long.valueOf(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            getClaimsFromToken(authToken);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            System.out.println("JWT was expired or incorrect");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
        }
        return false;
    }


    private Claims getClaimsFromToken(String token) {
        SecretKey key = generateKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
