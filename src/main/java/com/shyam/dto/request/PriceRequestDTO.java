package com.shyam.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequestDTO {
    private BigDecimal price;
}
