package com.shyam.dto.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductByNameRequestDTO implements Serializable {
    private String name;
}
