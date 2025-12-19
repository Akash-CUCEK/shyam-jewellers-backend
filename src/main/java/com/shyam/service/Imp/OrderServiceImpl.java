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
    public AddOrderResponseDTO createOrder(AddOrderRequestDTO addOrderRequestDTO) {
        logger.info("Create order processing the request");
        orderMapper.createOrder(addOrderRequestDTO);
        return orderMapper.mapToOrderCreateInMessage(messageSourceUtil
                .getMessage(MESSAGE_CODE_CREATE_ORDER));
    }

    @Override
    public GetOrderByIdResponseDTO getOrderById(GetOrderByIdRequestDTO getOrderByIdRequestDTO) {
        logger.info("get by Id processing the request");
        return orderMapper.getOrderByOrderId(getOrderByIdRequestDTO);
    }

    @Override
    public GetOrderByDateResponseDTO getOrderByDate(GetOrderByDateRequestDTO getOrderBydateRequestDTO) {
        logger.info("get by date processing the request");
        return orderMapper.getOrderByDate(getOrderBydateRequestDTO);
    }

    @Override
    public GetTotalOrderMonthResponse getTotalOrderMonth() {
        logger.info("");
        return orderMapper.getTotalOrderMonth();
    }

    @Override
    public GetOrderInvoiceResponse getOrderInvoice(GetOrderInvoiceRequest getOrderInvoiceRequest) {
        logger.info("");
        return orderMapper.getOrderInvoice(getOrderInvoiceRequest.getOrderId());
    }
}
