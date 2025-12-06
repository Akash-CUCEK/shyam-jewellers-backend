package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.AddOrderRequestDTO;
import com.shyam.dto.request.GetOrderByDateRequestDTO;
import com.shyam.dto.request.GetOrderByIdRequestDTO;
import com.shyam.dto.request.GetOrderInvoiceRequest;
import com.shyam.dto.response.*;
import com.shyam.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private  final OrderService orderService;

    @Operation(summary = "Create a order", description = "Creating a order.")
    @PostMapping("/createOrder")
    public BaseResponseDTO<AddOrderResponseDTO> addOrder(
            @RequestBody AddOrderRequestDTO addOrderRequestDTO,
            @RequestHeader String requestId
    ){
        logger.info("Received request for create order for requestId : {}",requestId);
        var response = orderService.createOrder(addOrderRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @Operation(summary = "get order by Id", description = "Getting order details by Id.")
    @PostMapping("/getOrderById")
    public BaseResponseDTO<GetOrderByIdResponseDTO> getOrderById(
            @RequestBody GetOrderByIdRequestDTO getOrderByIdRequestDTO,
            @RequestHeader String requestId
    ){
        logger.info("Received request for get order by Id for requestId : {}",requestId);
        var response = orderService.getOrderById(getOrderByIdRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);

    }

    @Operation(summary = "get order by date", description = "Getting order details by date.")
    @PostMapping("/getOrderByDate")
    public BaseResponseDTO<GetOrderByDateResponseDTO> getOrderByDate(
            @RequestBody GetOrderByDateRequestDTO getOrderBydateRequestDTO,
            @RequestHeader String requestId
    ){
        logger.info("Received request for get order by date for requestId : {}",requestId);
        var response = orderService.getOrderByDate(getOrderBydateRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);

    }

    @Operation(summary = "get total count order", description = "Getting total order of month")
    @PostMapping("/getTotalOrderMonth")
    public BaseResponseDTO<GetTotalOrderMonthResponse> getTotalOrderMonth(
            @RequestHeader String requestId
    ){
        logger.info("Received request for getting total order in month for requestId : {}",requestId);
        var response = orderService.getTotalOrderMonth(requestId);
        return new BaseResponseDTO<>(response,null,requestId);

    }

    @Operation(summary = "Generate PDF Invoice", description = "Generates order invoice PDF and returns it as a downloadable file")
    @PostMapping("/getOrderInvoiceById")
    public BaseResponseDTO<GetOrderInvoiceResponse> getOrderInvoice(
            @RequestHeader String requestId,
            @RequestBody GetOrderInvoiceRequest getOrderInvoiceRequest
    ){
        logger.info("Received request for generate PDF Invoice for requestId : {}",requestId);
        var response = orderService.getOrderInvoice(requestId,getOrderInvoiceRequest);
        return new BaseResponseDTO<>(response,null,requestId);

    }
}
