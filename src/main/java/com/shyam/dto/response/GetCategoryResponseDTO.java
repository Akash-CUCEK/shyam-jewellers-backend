package com.shyam.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetCategoryResponseDTO implements Serializable {
    List<GetCategoriesResponseDTO> getCategoriesResponseDTO;
}
