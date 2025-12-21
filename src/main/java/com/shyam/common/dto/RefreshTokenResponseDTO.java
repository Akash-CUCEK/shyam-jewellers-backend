package com.shyam.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RefreshTokenResponseDTO implements Serializable {
    private String accessToken;
}
