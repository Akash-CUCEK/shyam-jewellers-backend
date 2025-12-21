package com.shyam.common.service;

import com.shyam.common.dto.RefreshTokenDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(1);

    public void store(String email, String role, String refreshToken) {
        String hashedToken = passwordEncoder.encode(refreshToken);

        redisTemplate.opsForValue().set(
                "refresh:" + role + ":" + email,
                hashedToken,
                REFRESH_TOKEN_TTL
        );
    }

    public RefreshTokenDetails validate(String refreshToken) {
        Set<String> keys = redisTemplate.keys("refresh:*");

        for (String key : keys) {
            String storedHash = redisTemplate.opsForValue().get(key);

            if (storedHash != null &&
                    passwordEncoder.matches(refreshToken, storedHash)) {

                String[] parts = key.split(":");
                return new RefreshTokenDetails(parts[1], parts[2]); // role, email
            }
        }
        return null;
    }

    public void deleteByRefreshToken(String refreshToken) {
        Set<String> keys = redisTemplate.keys("refresh:*");
        if (keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            String storedHash = redisTemplate.opsForValue().get(key);

            if (storedHash != null &&
                    passwordEncoder.matches(refreshToken, storedHash)) {

                redisTemplate.delete(key);
                return;
            }
        }
    }
}
