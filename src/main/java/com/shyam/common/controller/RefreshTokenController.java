package com.shyam.common.controller;

import com.shyam.common.dto.RefreshTokenResponseDTO;
import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.common.jwt.JwtUtil;
import com.shyam.common.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    @PostMapping("/refreshToken")
    public ResponseEntity<BaseResponseDTO<RefreshTokenResponseDTO>> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponseDTO<>(null, null));
        }

        var details = refreshTokenService.validate(refreshToken);

        if (details == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponseDTO<>(null, null));
        }

        var newAccessToken =
                JwtUtil.generateAccessToken(details.email(), details.role());

        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        new RefreshTokenResponseDTO(newAccessToken),
                        null
                )
        );
    }

}
