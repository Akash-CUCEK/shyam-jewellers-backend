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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Operation(summary = "Add new product", description = "Admin adds a new product")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/addProduct")
    public BaseResponseDTO<ProductAddResponseDTO> addProduct(
            @Valid @RequestBody ProductAddRequestDTO addRequestDTO
    ) {
        logger.info("Add product");
        var response = productService.addProduct(addRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Update product", description = "Admin updates a product")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/updateProduct")
    public BaseResponseDTO<UpdateResponseDTO> updateProduct(
            @Valid @RequestBody UpdateRequestDTO updateRequestDTO
    ) {
        logger.info("Update product" );
        var response = productService.updateProduct(updateRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Delete product", description = "Admin deletes a product")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/deleteProduct")
    public BaseResponseDTO<DeleteResponseDTO> deleteProduct(
            @Valid @RequestBody DeleteProductRequestDTO deleteProductRequestDTO

    ) {
        logger.info("Delete product ");
        var response = productService.deleteProduct(deleteProductRequestDTO);
        return new BaseResponseDTO<>(response, null );
    }

    @Operation(summary = "Get all products", description = "Get all products")
    @PostMapping("/search/getAllProducts")
    public BaseResponseDTO<GetProductResponseDTO> getAllProduct()
    {
        logger.info("Received request to get all products" );
        var response = productService.getAllProduct();
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Get products by price", description = "Search product(s) within price range")
    @PostMapping("/search/price")
    public BaseResponseDTO<GetProductResponseDTO> getPriceProduct(
            @Valid @RequestBody PriceRequestDTO priceRequestDTO
    ) {
        logger.info("Search product by price" );
        var response = productService.getPriceProduct(priceRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Get products by Name", description = "Getting product by Product Name")
    @PostMapping("/search/name")
    public BaseResponseDTO<ProductResponseDTO> getNameProduct(
            @Valid @RequestBody GetProductByNameRequestDTO getProductByNameRequestDTO
    ) {
        logger.info("Search product by name");
        var response = productService.getNameProduct(getProductByNameRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Get products by category", description = "Search product(s) by category")
    @PostMapping("/search/category")
    public BaseResponseDTO<CategoryResponseDTO> getCategoryProduct(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        logger.info("Search product by category" );
        var response = productService.getCategoryProduct(categoryRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Get products by gender", description = "Search product(s) by gender")
    @PostMapping("/search/gender")
    public BaseResponseDTO<GenderResponseDTO> getGenderProduct(
            @Valid @RequestBody GenderRequestDTO genderRequestDTO
    ) {
        logger.info("Search product by gender" );
        var response = productService.getGenderProduct(genderRequestDTO);
        return new BaseResponseDTO<>(response, null);
    }

    @Operation(summary = "Get filtered products", description = "Returns products based on multiple filters")
    @PostMapping("/products/filter")
    public BaseResponseDTO<ProductFilterResponseDTO> getFilteredProducts(
            @Valid @RequestBody ProductFilterRequestDTO filterDTO
    ) {
        logger.info("Filtering products");
        var response = productService.getFilteredProducts(filterDTO);
        return new BaseResponseDTO<>(response, null );
    }


}
