package com.artinus.gateway.helper;

import com.artinus.gateway.infra.RedisOperation;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret.key}") String SECRET_KEY;
    private final RedisOperation redisOperation;

    public boolean validateToken(String token) {
        try {
            if(isBlackList(token)) {
                return false;
            }
            return !Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            log.error(String.format("[JWT EXCEPTION] %s", e.getMessage()));
            return false;
        }
    }

    private boolean isBlackList(String token) {
        if (Strings.isBlank(token)) {
            return false;
        }
        String makeRedisKey = "access-token:" + token;
        return redisOperation.hasKey(makeRedisKey);
    }
}
