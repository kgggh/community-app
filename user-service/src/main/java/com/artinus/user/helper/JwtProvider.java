package com.artinus.user.helper;

import com.artinus.user.exception.JwtInvalidException;
import com.artinus.user.infra.RedisOperation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    private final RedisOperation redisOperation;

    public final long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60 * 60;

    public String makeAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRED_TIME)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims getPayload(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private long getTokenExpiredSeconds(String token) {
        try {
            return getPayload(token).getExpiration().getTime();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new JwtInvalidException("[INVALID] jwt invalid... ");
        }
    }

    public void addToBlackList(String token) {
        long tokenExpiredSeconds = getTokenExpiredSeconds(token);
        long remainingTime = calculateReamingTime(tokenExpiredSeconds);
        redisOperation.add("access-token:" + token, token, remainingTime, TimeUnit.MILLISECONDS);
    }

    private long calculateReamingTime(long tokenExpiredSeconds) {
        Long now = new Date().getTime();
        return tokenExpiredSeconds - now;
    }
}
