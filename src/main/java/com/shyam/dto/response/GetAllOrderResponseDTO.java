package com.shyam.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderResponseDTO {
    private List<GetOrderByIdResponseDTO> getOrderByDateResponseDTOList;
}
