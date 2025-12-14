package com.shyam.entity;

import com.shyam.common.constants.ServiceType;
import com.shyam.common.constants.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String mobileNumber;

    private String notes;

    private String createdBy;
    private LocalDateTime createdAt;

    private String updatedBy;
    private LocalDateTime updatedAt;

}
