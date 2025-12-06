package com.shyam.common.jwt;

import com.shyam.common.redis.service.TokenBlacklistService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final TokenBlacklistService tokenBlacklistService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "refreshToken:";
    private static final long REFRESH_TOKEN_EXPIRY = 24 * 60 * 60;

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            JwtConstants.SECRET.getBytes(StandardCharsets.UTF_8)
    );

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 24 *60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public static String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

        redisTemplate.opsForValue().set(
                "refreshToken:" + refreshToken,
                userId,
                Duration.ofMillis(REFRESH_TOKEN_EXPIRATION_TIME)
        );

        log.info("Generated and stored refresh token for userId={}", userId);
        return refreshToken;
    }



    public boolean validateToken(String token) {
        try {
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                log.warn("Token is blacklisted: {}", token);
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    public String validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            var userId = claims.getSubject();

            var storedUserId = redisTemplate.opsForValue().get(PREFIX + token);

            if (storedUserId == null) {
                log.warn("Refresh token not found in Redis or expired: {}", token);
                return null;
            }

            if (!storedUserId.equals(userId)) {
                log.error("Token tampered! UserId mismatch: jwt={}, redis={}", userId, storedUserId);
                return null;
            }

            log.info("Refresh token validated for userId={}", userId);
            return userId;

        } catch (ExpiredJwtException e) {
            log.warn("Refresh token expired at {}", e.getClaims().getExpiration());
            return null;
        } catch (JwtException e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            return null;
        }
    }
    public void storeRefreshToken(String token, String userId) {
        redisTemplate.opsForValue()
                .set(PREFIX + token, userId, Duration.ofSeconds(REFRESH_TOKEN_EXPIRY));
        log.info("Stored refreshToken for userId={} with expiry={}s", userId, REFRESH_TOKEN_EXPIRY);
    }



    public void deleteRefreshToken(String token) {
        redisTemplate.delete(PREFIX + token);
        log.info("Deleted refresh token={}", token);
    }


    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public static Date getExpiry(String token) {
        return getClaims(token).getExpiration();
    }

    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
