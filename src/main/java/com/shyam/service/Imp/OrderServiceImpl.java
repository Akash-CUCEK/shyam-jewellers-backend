package com.shyam.service.Imp;

import com.shyam.common.util.MessageSourceUtil;
import com.shyam.dto.request.AddOrderRequestDTO;
import com.shyam.dto.request.GetOrderByDateRequestDTO;
import com.shyam.dto.request.GetOrderByIdRequestDTO;
import com.shyam.dto.request.GetOrderInvoiceRequest;
import com.shyam.dto.response.*;
import com.shyam.mapper.OrderMapper;
import com.shyam.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.shyam.constants.MessageConstant.MESSAGE_CODE_CREATE_ORDER;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final MessageSourceUtil messageSourceUtil;


    @Override
    public AddOrderResponseDTO createOrder(AddOrderRequestDTO addOrderRequestDTO, String requestId) {
        logger.info("Create order processing the request for requestId : {}",requestId);
        orderMapper.createOrder(addOrderRequestDTO,requestId);
        return orderMapper.mapToOrderCreateInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_CREATE_ORDER));
    }

    @Override
    public GetOrderByIdResponseDTO getOrderById(GetOrderByIdRequestDTO getOrderByIdRequestDTO, String requestId) {
        logger.info("get by Id processing the request for requestId : {}",requestId);
        return orderMapper.getOrderByOrderId(getOrderByIdRequestDTO,requestId);
    }

    @Override
    public GetOrderByDateResponseDTO getOrderByDate(GetOrderByDateRequestDTO getOrderBydateRequestDTO, String requestId) {
        logger.info("get by date processing the request for requestId : {}",requestId);
        return orderMapper.getOrderByDate(getOrderBydateRequestDTO,requestId);
    }

    @Override
    public GetTotalOrderMonthResponse getTotalOrderMonth(String requestId) {
        logger.info("");
        return orderMapper.getTotalOrderMonth(requestId);
    }

    @Override
    public GetOrderInvoiceResponse getOrderInvoice(String requestId, GetOrderInvoiceRequest getOrderInvoiceRequest) {
        logger.info("");
        return orderMapper.getOrderInvoice(getOrderInvoiceRequest.getOrderId());
    }
}
