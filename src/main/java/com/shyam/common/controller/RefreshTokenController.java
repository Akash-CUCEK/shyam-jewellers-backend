package com.shyam.common.controller;

import com.shyam.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken) {
        log.info("Refresh token request received");

        var userId = jwtUtil.validateRefreshToken(refreshToken);
        if (userId == null) {
            log.warn("Refresh failed. Invalid token={}", refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        var newAccessToken = JwtUtil.generateAccessToken(userId, "USER"); // role dynamically bhi le sakte
        log.info("Issued new access token for userId={}", userId);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
