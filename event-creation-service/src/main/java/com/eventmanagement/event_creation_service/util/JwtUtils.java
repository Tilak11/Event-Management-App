package com.eventmanagement.event_creation_service.util;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.eventmanagement.event_creation_service.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final Long JWT_EXPIRATION = 60L;
    private static final int MS_IN_MIN = 60000;

    public String generateJwt(User user) {
        return Jwts.builder().claim("name", user.getName())
                .setSubject(user.getName()).setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(JWT_EXPIRATION, ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public int getExpiration(String token) {
        int remainingTime = (int) (Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
                .getExpiration().getTime() - System.currentTimeMillis()) / MS_IN_MIN;
        return remainingTime;
    }
}
