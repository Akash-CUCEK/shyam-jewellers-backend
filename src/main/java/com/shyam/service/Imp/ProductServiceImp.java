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
    public ProductAddResponseDTO addProduct(ProductAddRequestDTO productAddRequestDTO ) {
        logger.info("Processing add product request" );
        Products savedProduct = productMapper.addProduct(productAddRequestDTO );
        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_ADDED);
        return productMapper.mapToAddProductResponse(savedProduct, message);
    }


    @Override
    @Transactional
    public UpdateResponseDTO updateProduct(UpdateRequestDTO updateRequestDTO ) {
        logger.info("Processing update product request");

        productMapper.updateProduct(updateRequestDTO);

        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_UPDATED);

        return productMapper.mapToUpdateProductResponse(message);
    }

    @Override
    @Transactional
    public DeleteResponseDTO deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO ) {
        logger.info("Processing delete product request");

        productMapper.deleteProduct(deleteProductRequestDTO.getName());

        var message = messageSourceUtil.getMessage(MESSAGE_CODE_PRODUCT_DELETED);

        return productMapper.mapToDeleteProductResponse(message);    }

    @Override
    public GetProductResponseDTO getPriceProduct(PriceRequestDTO priceRequestDTO ) {
        logger.info("Processing to find product based on price" );
        return productMapper.priceProduct(priceRequestDTO.getPrice());

    }

    @Override
    public CategoryResponseDTO getCategoryProduct(CategoryRequestDTO categoryRequestDTO ) {
        logger.info("Processing to find product based on category" );
        return productMapper.getCategoryProduct(categoryRequestDTO.getCategory());
    }

    @Override
    public GenderResponseDTO getGenderProduct(GenderRequestDTO genderRequestDTO) {
        logger.info("Processing to find product based on gender");
        return productMapper.getGenderProduct(genderRequestDTO.getGender());
    }

    @Override
    public ProductResponseDTO getNameProduct(GetProductByNameRequestDTO getProductByNameRequestDTO ) {
        logger.info("Processing to find product based on name");
        return productMapper.getNameProduct(getProductByNameRequestDTO.getName());
    }

    @Override
    public GetProductResponseDTO getAllProduct() {
        logger.info("Processing to find all products");
        return productMapper.getAllProduct();
    }

    @Override
    public ProductFilterResponseDTO getFilteredProducts(ProductFilterRequestDTO filterDTO) {
        logger.info("Filtering products based on multiple filters");
        return productMapper.getFilteredProducts(filterDTO);
    }
}
