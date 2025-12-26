package com.shyam.dto.response;

import com.shyam.common.constants.ServiceType;
import com.shyam.common.constants.Status;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeServiceResponseDTO implements Serializable {
    private Long serviceId;
    private String name;
    private String phoneNumber;
    private String address;
    private String notes;
    private ServiceType serviceType;
    private LocalDateTime createdAt;
    private Status status;
}
