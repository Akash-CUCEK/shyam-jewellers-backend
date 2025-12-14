package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInResponseDTO implements Serializable {
    private String message;
}
