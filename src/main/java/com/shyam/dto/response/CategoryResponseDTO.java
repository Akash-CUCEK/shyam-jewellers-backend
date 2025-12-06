package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO implements Serializable {
    private List<AllProductResponseDTO> products;
}
