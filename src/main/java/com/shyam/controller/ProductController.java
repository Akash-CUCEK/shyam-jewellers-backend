package com.shyam.controller;

import com.shyam.common.exception.dto.BaseResponseDTO;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Operation(summary = "Add new product", description = "Admin adds a new product")
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public BaseResponseDTO<ProductAddResponseDTO> addProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody ProductAddRequestDTO addRequestDTO
    ) {
        logger.info("Add product | requestId: {}", requestId);
        var response = productService.addProduct(addRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Update product", description = "Admin updates a product")
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProduct")
    public BaseResponseDTO<UpdateResponseDTO> updateProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody UpdateRequestDTO updateRequestDTO
    ) {
        logger.info("Update product | requestId: {}", requestId);
        var response = productService.updateProduct(updateRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Delete product", description = "Admin deletes a product")
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct")
    public BaseResponseDTO<DeleteResponseDTO> deleteProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody DeleteProductRequestDTO deleteProductRequestDTO

    ) {
        logger.info("Delete product | requestId: {}", requestId);
        var response = productService.deleteProduct(deleteProductRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get all products", description = "Get all products")
    @PostMapping("/search/getAllProducts")
    public BaseResponseDTO<GetProductResponseDTO> getAllProduct(
            @RequestHeader String requestId
    ) {
        logger.info("Received request to get all products | requestId: {}", requestId);
        var response = productService.getAllProduct(requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get products by price", description = "Search product(s) within price range")
    @PostMapping("/search/price")
    public BaseResponseDTO<GetProductResponseDTO> getPriceProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody PriceRequestDTO priceRequestDTO
    ) {
        logger.info("Search product by price | requestId: {}", requestId);
        var response = productService.getPriceProduct(priceRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get products by Name", description = "Getting product by Product Name")
    @PostMapping("/search/name")
    public BaseResponseDTO<ProductResponseDTO> getNameProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody GetProductByNameRequestDTO getProductByNameRequestDTO
    ) {
        logger.info("Search product by name | requestId: {}", requestId);
        var response = productService.getNameProduct(getProductByNameRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get products by category", description = "Search product(s) by category")
    @PostMapping("/search/category")
    public BaseResponseDTO<CategoryResponseDTO> getCategoryProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        logger.info("Search product by category | requestId: {}", requestId);
        var response = productService.getCategoryProduct(categoryRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get products by gender", description = "Search product(s) by gender")
    @PostMapping("/search/gender")
    public BaseResponseDTO<GenderResponseDTO> getGenderProduct(
            @RequestHeader String requestId,
            @Valid @RequestBody GenderRequestDTO genderRequestDTO
    ) {
        logger.info("Search product by gender | requestId: {}", requestId);
        var response = productService.getGenderProduct(genderRequestDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }

    @Operation(summary = "Get filtered products", description = "Returns products based on multiple filters")
    @PostMapping("/products/filter")
    public BaseResponseDTO<ProductFilterResponseDTO> getFilteredProducts(
            @RequestHeader String requestId,
            @Valid @RequestBody ProductFilterRequestDTO filterDTO
    ) {
        logger.info("Filtering products | requestId: {}", requestId);
        var response = productService.getFilteredProducts(filterDTO, requestId);
        return new BaseResponseDTO<>(response, null, requestId);
    }


}
