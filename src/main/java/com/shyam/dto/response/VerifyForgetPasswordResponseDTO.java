package com.shyam.dto.response;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyForgetPasswordResponseDTO implements Serializable {
    private String response;
}
