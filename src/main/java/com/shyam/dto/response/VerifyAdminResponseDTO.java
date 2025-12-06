package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyAdminResponseDTO implements Serializable {
    private String token;
    private String message;
}
