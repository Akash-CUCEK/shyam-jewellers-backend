package com.shyam.dto.response;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllProductResponseDTO {
    private String name;
    private BigDecimal price;
    private BigDecimal weight;
    private Integer availableStock;
    private Boolean isAvailable;
}
