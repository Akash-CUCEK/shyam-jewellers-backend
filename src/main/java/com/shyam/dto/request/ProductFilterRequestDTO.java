package com.shyam.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequestDTO {
        private String category;
        private Double minPrice;
        private Double maxPrice;
        private Double minWeight;
        private Double maxWeight;
        private String materialType;
        private String gender;
        private Boolean isAvailable;
        private Integer minAvailableStock;
        private Integer maxAvailableStock;
}

