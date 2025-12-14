package com.shyam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer discountPercentage;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private String materialType;

    @Column(nullable = false, unique = true)
    private String skuCode;

    @Column(length = 200)
    private String shortDescription;

    @Lob
    private String fullDescription;

    private String gender;

    private Double averageRating;

    private Boolean isAvailable;

    private Integer quantity;

    private Integer availableStock;

    private String imageUrl;

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.skuCode = "SKU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PostPersist
    public void setAutoName() {
        if (this.name == null) {
            this.name = this.category + " #" + this.id;
        }
    }
}
