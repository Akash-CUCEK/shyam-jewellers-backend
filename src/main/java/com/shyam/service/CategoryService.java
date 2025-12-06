package com.shyam.service;

import com.shyam.dto.request.AddCategoryRequestDTO;
import com.shyam.dto.request.GetCategoryByIdRequestDTO;
import com.shyam.dto.response.AddCategoryResponseDTO;
import com.shyam.dto.response.GetCategoryByIdResponseDTO;
import com.shyam.dto.response.GetCategoryResponseDTO;
import com.shyam.dto.response.UpdateCategoryResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    GetCategoryResponseDTO getAllCategories(String requestId);

    AddCategoryResponseDTO addCategories(AddCategoryRequestDTO addCategoryRequestDTO, String requestId);

    UpdateCategoryResponseDTO updateCategoryRequestDTO(AddCategoryRequestDTO updateCategoryRequestDTO, String requestId);

    GetCategoryByIdResponseDTO getCategory(String requestId, GetCategoryByIdRequestDTO getCategoryByIdRequestDTO);

    UpdateCategoryResponseDTO deleteCategory(GetCategoryByIdRequestDTO updateCategoryRequestDTO, String requestId);

    ResponseEntity<?> uploadExcel(String requestId, MultipartFile file, String createdBy);
}


