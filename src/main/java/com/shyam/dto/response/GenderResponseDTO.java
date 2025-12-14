package com.shyam.dto.response;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderResponseDTO implements Serializable {
    private List<AllProductResponseDTO> products;
}
