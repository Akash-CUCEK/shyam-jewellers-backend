package com.shyam.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderResponseDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;
    private List<String> products; // You can use List<ProductDTO> if needed
    private LocalDateTime orderDateTime;
    private String orderStatus;
    private Double totalCost;
    private Double dueAmount;
}
