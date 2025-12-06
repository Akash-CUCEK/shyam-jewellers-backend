package com.shyam.mapper;

import com.shyam.dao.ProductDAO;
import com.shyam.dto.request.ProductAddRequestDTO;
import com.shyam.dto.request.ProductFilterRequestDTO;
import com.shyam.dto.request.UpdateRequestDTO;
import com.shyam.dto.response.*;
import com.shyam.entity.Products;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProductMapper.class);
    private final ProductDAO productDAO;

    public Products addProduct(ProductAddRequestDTO dto, String requestId) {
        logger.info("Adding product in mapper for requestId: {}", requestId);
        Products product = new Products();

        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountPercentage(dto.getDiscountPercentage());
        product.setWeight(dto.getWeight());
        product.setGender(dto.getGender());
        product.setMaterialType(dto.getMaterialType());
        product.setShortDescription(dto.getShortDescription());
        product.setFullDescription(dto.getFullDescription());
        product.setIsAvailable(dto.getIsAvailable());
        product.setQuantity(dto.getQuantity());
        product.setAvailableStock(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setCreatedBy(dto.getEmail());
        product.setUpdatedBy(dto.getEmail());
        return productDAO.save(product);
    }


    public ProductAddResponseDTO mapToAddProductResponse(Products product, String message) {
        return ProductAddResponseDTO.builder()
                .name(product.getName())
                .message(message)
                .build();
    }

    public void updateProduct(UpdateRequestDTO updateRequestDTO, String requestId) {
        logger.info("Updating product in mapper for requestId: {}", requestId);

        // 1. Fetch product by name
        var product = productDAO.findProduct(updateRequestDTO.getName());

        // 2. Update only editable fields
        product.setCategory(updateRequestDTO.getCategory());
        product.setPrice(updateRequestDTO.getPrice());
        product.setDiscountPercentage(updateRequestDTO.getDiscountPercentage());
        product.setWeight(updateRequestDTO.getWeight());
        product.setMaterialType(updateRequestDTO.getMaterialType());
        product.setShortDescription(updateRequestDTO.getShortDescription());
        product.setFullDescription(updateRequestDTO.getFullDescription());
        product.setGender(updateRequestDTO.getGender());
        product.setIsAvailable(updateRequestDTO.getIsAvailable());
        product.setQuantity(updateRequestDTO.getQuantity());
        product.setAvailableStock(updateRequestDTO.getAvailableStock());
        product.setImageUrl(updateRequestDTO.getImageUrl());
        product.setUpdatedBy(updateRequestDTO.getUpdatedBy()); // if you're tracking

        // 3. Save updated product
        productDAO.save(product);

        logger.info("Product updated successfully for request: {}", requestId);
    }


    public UpdateResponseDTO mapToUpdateProductResponse(String message) {
        return UpdateResponseDTO.builder()
                .message(message)
                .build();
    }

    public void deleteProduct(String name, String requestId) {
        logger.info("Deleting product in mapper for requestId: {}", requestId);
        var product =productDAO.findProduct(name);
        productDAO.delete(product);
        logger.info("Product '{}' deleted successfully for requestId: {}", name, requestId);
    }

    public DeleteResponseDTO mapToDeleteProductResponse(String message) {
        return DeleteResponseDTO.builder()
                .message(message)
                .build();
    }

    public GetProductResponseDTO priceProduct(BigDecimal price, String requestId) {
        logger.info("Finding products under price {} for requestId: {}", price, requestId);

        List<Products> products = productDAO.priceProduct(price);

        List<AllProductResponseDTO> responseList = products.stream()
                .map(this::mapTOAllProductResponseDTO)
                .toList();

        return GetProductResponseDTO.builder()
                .products(responseList)
                .build();
    }

    public CategoryResponseDTO getCategoryProduct(String category, String requestId) {
        logger.info("Finding products under category {} for requestId: {}", category, requestId);

        List<Products> products = productDAO.getCategoryProduct(category);

        List<AllProductResponseDTO> responseList = products.stream()
                .map(this::mapTOAllProductResponseDTO)
                .toList();

        return CategoryResponseDTO.builder()
                .products(responseList)
                .build();
    }

    public GenderResponseDTO getGenderProduct(String gender, String requestId) {
        logger.info("Finding products under gender {} for requestId: {}", gender, requestId);

        List<Products> products = productDAO.getGenderProduct(gender);

        List<AllProductResponseDTO> responseList = products.stream()
                .map(this::mapTOAllProductResponseDTO)
                .toList();

        return GenderResponseDTO.builder()
                .products(responseList)
                .build();
    }
    private AllProductResponseDTO mapTOAllProductResponseDTO(Products p){
        return AllProductResponseDTO.builder()
                .name(p.getName())
                .availableStock(p.getAvailableStock())
                .price(p.getPrice())
                .weight(p.getWeight())
                .isAvailable(p.getIsAvailable())
                .build();
    }

    private ProductResponseDTO mapToProductResponseDTO(Products p) {
        return ProductResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .availableStock(p.getAvailableStock())
                .category(p.getCategory())
                .price(p.getPrice())
                .discountPercentage(p.getDiscountPercentage())
                .weight(p.getWeight())
                .materialType(p.getMaterialType())
                .skuCode(p.getSkuCode())
                .shortDescription(p.getShortDescription())
                .fullDescription(p.getFullDescription())
                .gender(p.getGender())
                .averageRating(p.getAverageRating())
                .isAvailable(p.getIsAvailable())
                .imageUrl(p.getImageUrl())
                .build();
    }

    public ProductResponseDTO getNameProduct(String name) {
        var product = productDAO.findProduct(name);
        return mapToProductResponseDTO(product);
    }

    public GetProductResponseDTO getAllProduct() {
        List<Products> products = productDAO.getAllProduct();
        List<AllProductResponseDTO> responseList = products.stream()
                .map(this::mapTOAllProductResponseDTO)
                .toList();
        return GetProductResponseDTO.builder()
                .products(responseList)
                .build();
    }


    public ProductFilterResponseDTO getFilteredProducts(ProductFilterRequestDTO filterDTO, String requestId) {
        List<Products> products = productDAO.findProductsByFilters(
                filterDTO.getCategory(),
                filterDTO.getMinPrice(),
                filterDTO.getMaxPrice(),
                filterDTO.getMinWeight(),
                filterDTO.getMaxWeight(),
                filterDTO.getMaterialType(),
                filterDTO.getGender(),
                filterDTO.getIsAvailable(),
                filterDTO.getMinAvailableStock(),
                filterDTO.getMaxAvailableStock()
        );
        List<AllProductResponseDTO> responseList = products.stream()
                .map(this::mapTOAllProductResponseDTO)
                .toList();

        return ProductFilterResponseDTO.builder()
                .products(responseList)
                .build();
    }


}
