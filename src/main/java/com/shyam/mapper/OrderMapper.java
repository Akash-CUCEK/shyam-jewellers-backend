package com.shyam.mapper;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.shyam.dao.OrderDAO;
import com.shyam.dto.request.AddOrderRequestDTO;
import com.shyam.dto.request.GetOrderByDateRequestDTO;
import com.shyam.dto.request.GetOrderByIdRequestDTO;
import com.shyam.dto.response.*;
import com.shyam.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.itextpdf.text.Document;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.shyam.common.util.MapperUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderDAO orderDAO;

    public void createOrder(AddOrderRequestDTO addOrderRequestDTO, String requestId) {
        log.debug("Saving the new order for requestId : {}", requestId);
        var order = mapToOrderEntity(addOrderRequestDTO, requestId);
        orderDAO.save(order, requestId);
    }

    public GetOrderByIdResponseDTO getOrderByOrderId(GetOrderByIdRequestDTO getOrderByIdRequestDTO, String requestId) {
        log.debug("Getting the order by Id for requestId : {}", requestId);
        var order = orderDAO.findOrderByOrderId(getOrderByIdRequestDTO.getOrderId());
        return mapToOrderEntity(order);
    }

    public GetOrderByDateResponseDTO getOrderByDate(GetOrderByDateRequestDTO getOrderBydateRequestDTO, String requestId) {
        log.debug("Getting the order by date for requestId : {}", requestId);
        List<Order> orderList= orderDAO.findOrderByOrderDate(getOrderBydateRequestDTO);

        List<GetOrderByIdResponseDTO> responseList = orderList.stream()
                .map(this::mapToOrderEntity)
                .toList();

        return GetOrderByDateResponseDTO.builder()
                .getOrderByDateResponseDTOList(responseList)
                .build();
    }

    public GetOrderByIdResponseDTO mapToOrderEntity(Order order) {
        return GetOrderByIdResponseDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .customerPhone(order.getCustomerPhone())
                .address(order.getAddress())
                .productIds(order.getProductIds())
                .orderDate(order.getOrderDate())
                .orderTime(order.getOrderTime())
                .orderStatus(order.getOrderStatus().name())
                .deliveryType(order.getDeliveryType())
                .totalCost(order.getTotalCost().doubleValue())
                .dueAmount(order.getDueAmount().doubleValue())
                .paymentMethod(order.getPaymentMethod().name())
                .notes(order.getNotes())
                .createdById(order.getCreatedById())
                .createdByRole(order.getCreatedByRole().name())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public AddOrderResponseDTO mapToOrderCreateInMessage(String message) {
        return AddOrderResponseDTO.builder()
                .message(message)
                .build();
    }

    public Order mapToOrderEntity(AddOrderRequestDTO dto, String requestId) {
        LocalDateTime dateTime = dto.getOrderDateTime() != null
                ? dto.getOrderDateTime()
                : LocalDateTime.now();
        return Order.builder()
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .customerPhone(dto.getCustomerPhone())
                .address(dto.getAddress())
                .productIds(dto.getProducts())
                .orderDate(dateTime.toLocalDate())
                .orderTime(dateTime.toLocalTime())
                .orderStatus(parseOrderStatus(dto.getOrderStatus()))
                .deliveryType(dto.getDeliveryType())
                .totalCost(dto.getTotalCost())
                .dueAmount(dto.getDueAmount())
                .paymentMethod(parsePaymentMethod(dto.getPaymentMethod()))
                .notes(dto.getNotes())
                .createdById(parseCreatedById(dto.getCreatedBy()))
                .createdByRole(parseRole(dto.getCreatedByRole()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public GetTotalOrderMonthResponse getTotalOrderMonth(String requestId) {
        log.debug("Getting total order of the month for requestId: {}", requestId);
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        var count = orderDAO.countOrdersByOrderDateBetween(firstDayOfMonth, lastDayOfMonth);
        return GetTotalOrderMonthResponse.builder()
                .count(count != null ? count : 0L)
                .build();
    }

    public GetOrderInvoiceResponse getOrderInvoice(Long orderId) {
        log.debug("Generating invoice PDF for orderId: {}", orderId);
        var order = orderDAO.findOrderByOrderId(orderId);
        byte[] pdfBytes = generatePdf(order);

        return GetOrderInvoiceResponse.builder()
                .invoicePdfBytes(pdfBytes)
                .fileName("invoice_" + order.getId() + ".pdf")
                .build();
    }

    private byte[] generatePdf(Order order) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Header
            document.add(new Paragraph("***** Invoice *****"));
            document.add(new Paragraph("Generated On: " + LocalDateTime.now()));
            document.add(new Paragraph(" ")); // empty line

            // Order Details
            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Customer Name: " + order.getCustomerName()));
            document.add(new Paragraph("Customer Email: " + order.getCustomerEmail()));
            document.add(new Paragraph("Customer Phone: " + order.getCustomerPhone()));
            document.add(new Paragraph("Address: " + order.getAddress()));
            document.add(new Paragraph("Delivery Type: " + order.getDeliveryType()));
            document.add(new Paragraph("Order DateTime: " + order.getOrderDate() + " " + order.getOrderTime()));
            document.add(new Paragraph("Order Status: " + order.getOrderStatus()));
            document.add(new Paragraph("Payment Method: " + order.getPaymentMethod()));
            document.add(new Paragraph("Payment Status: " + order.getPaymentStatus()));
            document.add(new Paragraph("Total Cost: ₹" + order.getTotalCost()));
            document.add(new Paragraph("Due Amount: ₹" + order.getDueAmount()));
            document.add(new Paragraph("Notes: " + order.getNotes()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Failed to generate PDF for orderId: {}", order.getId(), e);
            throw new RuntimeException("PDF Generation failed", e);
        }
    }


    public GetOrderInvoiceResponse getOrderInvoice(Order order, byte[] pdfBytes) {
        return GetOrderInvoiceResponse.builder()
                .invoicePdfBytes(pdfBytes)
                .fileName("invoice_" + order.getId() + ".pdf")
                .build();
    }

}
