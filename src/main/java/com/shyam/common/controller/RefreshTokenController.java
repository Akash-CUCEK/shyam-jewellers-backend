package com.shyam.common.controller;

import com.shyam.common.dto.RefreshTokenResponseDTO;
import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.common.jwt.JwtUtil;
import com.shyam.common.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refreshToken")
    public ResponseEntity<BaseResponseDTO<RefreshTokenResponseDTO>> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        log.info("Received refresh token request");

        if (refreshToken == null) {
            log.info("Empty refresh token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponseDTO<>(null, null));
        }

        var details = refreshTokenService.validate(refreshToken);
        if (details == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponseDTO<>(null, null));
        }

        refreshTokenService.deleteByRefreshToken(refreshToken);

        var newRefreshToken = JwtUtil.generateRefreshToken();
        refreshTokenService.store(
                details.email(),
                details.role(),
                newRefreshToken
        );

        var newAccessToken =
                JwtUtil.generateAccessToken(details.email(), details.role());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(
                        new BaseResponseDTO<>(
                                new RefreshTokenResponseDTO(newAccessToken),
                                null
                        )
                );
    }
}
