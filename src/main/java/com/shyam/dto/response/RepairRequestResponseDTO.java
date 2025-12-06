package com.shyam.dto.response;


import com.shyam.common.constants.Status;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestResponseDTO implements Serializable {
    private Long serviceId;
    private String name;
    private String address;
    private String notes;
    private String mobileNumber;
    private LocalDateTime createdAt;
    private Status status;
}
