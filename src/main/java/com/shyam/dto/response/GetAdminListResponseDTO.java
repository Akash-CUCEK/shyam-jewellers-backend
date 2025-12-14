package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAdminListResponseDTO implements Serializable {
    private List<GetAllAdminResponseDTO> getAllAdminResponseDTOList;
}
