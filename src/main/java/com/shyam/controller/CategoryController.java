package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.AddCategoryRequestDTO;
import com.shyam.dto.request.GetCategoryByIdRequestDTO;
import com.shyam.dto.response.AddCategoryResponseDTO;
import com.shyam.dto.response.GetCategoryByIdResponseDTO;
import com.shyam.dto.response.GetCategoryResponseDTO;
import com.shyam.dto.response.UpdateCategoryResponseDTO;
import com.shyam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/getAllCategory")
    public BaseResponseDTO<GetCategoryResponseDTO> getAllCategories() {
        log.info("Received request for getting all category");
        var response = categoryService.getAllCategories();
        return new BaseResponseDTO<>(response,null);
    }

    @PostMapping("/getCategory")
    public BaseResponseDTO<GetCategoryByIdResponseDTO> getCategory(
            @RequestBody GetCategoryByIdRequestDTO getCategoryByIdRequestDTO
    ) {
        log.info("Received request for get category by Id");
        var response = categoryService.getCategory(getCategoryByIdRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @PostMapping("/addCategory")
    public BaseResponseDTO<AddCategoryResponseDTO> addCategories(
            @RequestBody AddCategoryRequestDTO addCategoryRequestDTO
            ) {
        log.info("Received request for adding category");
        var response =  categoryService.addCategories(addCategoryRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("createdBy") String createdBy
    ) {
        log.info("Received excel request for adding category");
        return categoryService.uploadExcel(file,createdBy);
    }

    @PutMapping("/updateCategory")
    public BaseResponseDTO<UpdateCategoryResponseDTO> updateCategories(
            @RequestBody AddCategoryRequestDTO updateCategoryRequestDTO
    ) {
        log.info("Received request for updating category");
        var response =  categoryService.updateCategoryRequestDTO(updateCategoryRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

    @DeleteMapping("/deleteCategory")
    public BaseResponseDTO<UpdateCategoryResponseDTO> deleteCategory(
            @RequestBody GetCategoryByIdRequestDTO updateCategoryRequestDTO
    ) {
        log.info("Received request for deleting category");
        var response =  categoryService.deleteCategory(updateCategoryRequestDTO);
        return new BaseResponseDTO<>(response,null);
    }

}
