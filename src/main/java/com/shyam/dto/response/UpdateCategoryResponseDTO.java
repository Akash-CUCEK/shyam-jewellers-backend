package com.shyam.dto.response;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryResponseDTO implements Serializable {
    private String response;
}
