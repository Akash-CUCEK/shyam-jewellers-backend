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
    public BaseResponseDTO<GetCategoryResponseDTO> getAllCategories(@RequestHeader String requestId) {
        log.info("Received request for getting all category for requestId : {}",requestId);
        var response = categoryService.getAllCategories(requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/getCategory")
    public BaseResponseDTO<GetCategoryByIdResponseDTO> getCategory(
            @RequestHeader String requestId,
            @RequestBody GetCategoryByIdRequestDTO getCategoryByIdRequestDTO
    ) {
        log.info("Received request for get category by Id for requestId : {}",requestId);
        var response = categoryService.getCategory(requestId,getCategoryByIdRequestDTO);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/addCategory")
    public BaseResponseDTO<AddCategoryResponseDTO> addCategories(
            @RequestHeader String requestId,
            @RequestBody AddCategoryRequestDTO addCategoryRequestDTO
            ) {
        log.info("Received request for adding category for requestId : {}",requestId);
        var response =  categoryService.addCategories(addCategoryRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcel(
            @RequestHeader String requestId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("createdBy") String createdBy
    ) {
        log.info("Received excel request for adding category for requestId : {}",requestId);
        return categoryService.uploadExcel(requestId,file,createdBy);
    }

    @PutMapping("/updateCategory")
    public BaseResponseDTO<UpdateCategoryResponseDTO> updateCategories(
            @RequestHeader String requestId,
            @RequestBody AddCategoryRequestDTO updateCategoryRequestDTO
    ) {
        log.info("Received request for updating category for requestId : {}",requestId);
        var response =  categoryService.updateCategoryRequestDTO(updateCategoryRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

    @DeleteMapping("/deleteCategory")
    public BaseResponseDTO<UpdateCategoryResponseDTO> deleteCategory(
            @RequestHeader String requestId,
            @RequestBody GetCategoryByIdRequestDTO updateCategoryRequestDTO
    ) {
        log.info("Received request for deleting category for requestId : {}",requestId);
        var response =  categoryService.deleteCategory(updateCategoryRequestDTO,requestId);
        return new BaseResponseDTO<>(response,null,requestId);
    }

}
