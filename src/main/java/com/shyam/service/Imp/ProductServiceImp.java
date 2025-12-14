package com.shyam.service.Imp;

import com.shyam.common.util.MessageSourceUtil;
import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import com.shyam.entity.Products;
import com.shyam.mapper.ProductMapper;
import com.shyam.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shyam.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
    private final ProductMapper productMapper;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional
    public ProductAddResponseDTO addProduct(ProductAddRequestDTO productAddRequestDTO, String requestId) {
        logger.info("Processing add product request: {}", requestId);
        Products savedProduct = productMapper.addProduct(productAddRequestDTO, requestId);
        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_ADDED);
        return productMapper.mapToAddProductResponse(savedProduct, message);
    }


    @Override
    @Transactional
    public UpdateResponseDTO updateProduct(UpdateRequestDTO updateRequestDTO, String requestId) {
        logger.info("Processing update product request: {}", requestId);

        productMapper.updateProduct(updateRequestDTO,requestId);

        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_UPDATED);

        return productMapper.mapToUpdateProductResponse(message);
    }

    @Override
    @Transactional
    public DeleteResponseDTO deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO, String requestId) {
        logger.info("Processing delete product request: {}", requestId);

        productMapper.deleteProduct(deleteProductRequestDTO.getName(),requestId);

        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_DELETED);

        return productMapper.mapToDeleteProductResponse(message);    }

    @Override
    public GetProductResponseDTO getPriceProduct(PriceRequestDTO priceRequestDTO, String requestId) {
        logger.info("Processing to find product based on price  request: {}", requestId);
        return productMapper.priceProduct(priceRequestDTO.getPrice(),requestId);

    }

    @Override
    public CategoryResponseDTO getCategoryProduct(CategoryRequestDTO categoryRequestDTO, String requestId) {
        logger.info("Processing to find product based on category for request: {}", requestId);
        return productMapper.getCategoryProduct(categoryRequestDTO.getCategory(),requestId);
    }

    @Override
    public GenderResponseDTO getGenderProduct(GenderRequestDTO genderRequestDTO, String requestId) {
        logger.info("Processing to find product based on gender for request: {}", requestId);
        return productMapper.getGenderProduct(genderRequestDTO.getGender(),requestId);
    }

    @Override
    public ProductResponseDTO getNameProduct(GetProductByNameRequestDTO getProductByNameRequestDTO, String requestId) {
        logger.info("Processing to find product based on name for request: {}", requestId);
        return productMapper.getNameProduct(getProductByNameRequestDTO.getName());
    }

    @Override
    public GetProductResponseDTO getAllProduct(String requestId) {
        logger.info("Processing to find all products for request: {}", requestId);
        return productMapper.getAllProduct();
    }

    @Override
    public ProductFilterResponseDTO getFilteredProducts(ProductFilterRequestDTO filterDTO, String requestId) {
        logger.info("Filtering products based on multiple filters for requestId: {}", requestId);
        return productMapper.getFilteredProducts(filterDTO, requestId);
    }
}
