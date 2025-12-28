package com.shyam.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer discountPercentage;
    private BigDecimal finalPrice;
    private BigDecimal weight;
    private String imageUrl;
    private String gender;
    private Boolean isAvailable;
    private Integer availableStock;
}
