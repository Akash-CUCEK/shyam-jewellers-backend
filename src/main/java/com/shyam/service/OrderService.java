package com.shyam.service;

import com.shyam.dto.request.AddOrderRequestDTO;
import com.shyam.dto.request.GetOrderByDateRequestDTO;
import com.shyam.dto.request.GetOrderByIdRequestDTO;
import com.shyam.dto.request.GetOrderInvoiceRequest;
import com.shyam.dto.response.*;

public interface OrderService {
    AddOrderResponseDTO createOrder(AddOrderRequestDTO addOrderRequestDTO, String requestId);

    GetOrderByIdResponseDTO getOrderById(GetOrderByIdRequestDTO getOrderByIdRequestDTO, String requestId);


    GetOrderByDateResponseDTO getOrderByDate(GetOrderByDateRequestDTO getOrderBydateRequestDTO, String requestId);

    GetTotalOrderMonthResponse getTotalOrderMonth(String requestId);

    GetOrderInvoiceResponse getOrderInvoice(String requestId, GetOrderInvoiceRequest getOrderInvoiceRequest);
}
