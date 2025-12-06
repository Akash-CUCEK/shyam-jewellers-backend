package com.shyam.dto.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderInvoiceRequest {
    private Long orderId;
}
