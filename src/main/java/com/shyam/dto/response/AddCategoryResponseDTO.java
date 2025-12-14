package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryResponseDTO implements Serializable {
    private String message;
}
