package com.shyam.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDTO<T extends Serializable> implements Serializable {
    private T response;
    private ErrorResponseDTO errors;
    private String requestId;
}
