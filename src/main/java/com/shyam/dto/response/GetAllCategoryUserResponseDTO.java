package com.shyam.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoryUserResponseDTO {
    private List<GetCategoryUserResponseDTO> getCategoryUserResponseDTOS;
}
