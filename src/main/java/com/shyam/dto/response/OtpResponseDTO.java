package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponseDTO implements Serializable {
    private String message;
    private String token;
}
