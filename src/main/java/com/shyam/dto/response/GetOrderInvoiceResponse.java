package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderInvoiceResponse implements Serializable {
    private byte[] invoicePdfBytes;
    private String fileName;
}

