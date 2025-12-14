package com.shyam.dto.request;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrderByDateRequestDTO {
    private LocalDate orderDate;
}
