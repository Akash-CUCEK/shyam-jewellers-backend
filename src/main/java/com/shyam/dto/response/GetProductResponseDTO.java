package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponseDTO implements Serializable {
    private List<AllProductResponseDTO> products;
}
