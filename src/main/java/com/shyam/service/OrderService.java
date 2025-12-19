package com.shyam.service;

import com.shyam.dto.request.AddOrderRequestDTO;
import com.shyam.dto.request.GetOrderByDateRequestDTO;
import com.shyam.dto.request.GetOrderByIdRequestDTO;
import com.shyam.dto.request.GetOrderInvoiceRequest;
import com.shyam.dto.response.*;

public interface OrderService {
    AddOrderResponseDTO createOrder(AddOrderRequestDTO addOrderRequestDTO );

    GetOrderByIdResponseDTO getOrderById(GetOrderByIdRequestDTO getOrderByIdRequestDTO);


    GetOrderByDateResponseDTO getOrderByDate(GetOrderByDateRequestDTO getOrderBydateRequestDTO );

    GetTotalOrderMonthResponse getTotalOrderMonth();

    GetOrderInvoiceResponse getOrderInvoice(GetOrderInvoiceRequest getOrderInvoiceRequest);
}
