package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAddResponseDTO implements Serializable {
    private String name;
    private String message;
}
