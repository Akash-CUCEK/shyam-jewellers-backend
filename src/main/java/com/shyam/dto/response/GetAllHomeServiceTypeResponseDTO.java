package com.shyam.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllHomeServiceTypeResponseDTO implements Serializable {
    private List<String> serviceTypes;
}
