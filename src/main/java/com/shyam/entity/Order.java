package com.shyam.entity;

import com.shyam.common.constants.OrderPaymentStatus;
import com.shyam.common.constants.OrderStatus;
import com.shyam.common.constants.PaymentMethod;
import com.shyam.common.constants.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;

    private LocalDate orderDate;
    private LocalTime orderTime;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String deliveryType;

    private BigDecimal totalCost;
    private BigDecimal dueAmount;

    @Enumerated(EnumType.STRING)
    private OrderPaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String notes;

    private Long createdById;

    @Enumerated(EnumType.STRING)
    private Role createdByRole;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // In Order entity
    @ElementCollection
    @CollectionTable(name = "order_product_ids", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

}
