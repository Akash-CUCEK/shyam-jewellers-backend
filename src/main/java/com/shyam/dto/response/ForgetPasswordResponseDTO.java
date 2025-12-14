package com.shyam.dto.response;

import java.io.Serializable;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgetPasswordResponseDTO implements Serializable {
    private String response;
}
