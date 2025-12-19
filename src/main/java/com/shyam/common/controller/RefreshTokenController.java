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
        if (jwtUtil.validateToken(refreshToken)) {
            log.warn("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        var newAccessToken = JwtUtil.generateAccessToken(jwtUtil.getUsername(refreshToken), jwtUtil.getRole(refreshToken));
        log.info("Error while generating refresh token");
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
