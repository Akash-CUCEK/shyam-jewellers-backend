package com.shyam.dao;

import com.shyam.common.exception.domain.SYMErrorType;
import com.shyam.common.exception.domain.SYMException;
import com.shyam.constants.ErrorCodeConstants;
import com.shyam.entity.Products;
import com.shyam.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);
    private final ProductRepository productRepository;

    public Products save(Products product) {
        try {
            logger.debug("Saving the Product");
            productRepository.save(product);
            return product;
        } catch (Exception e) {
            logger.error("Error while saving product", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to save product",
                    e.getMessage()
            );
        }
    }

    public void delete(Products product) {
        try {
            logger.debug("Deleting the product");
            productRepository.delete(product);
        } catch (Exception e) {
            logger.error("Error while deleting product", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to delete product",
                    e.getMessage()
            );
        }
    }

    public Products findProduct(String name) {
        try {
            return productRepository.findByName(name)
                    .orElseThrow(() -> new SYMException(
                            HttpStatus.NOT_FOUND,
                            SYMErrorType.GENERIC_EXCEPTION,
                            ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                            String.format("Product not found with name: %s", name),
                            String.format("No product found in DB for name: %s", name)
                    ));
        } catch (Exception e) {
            logger.error("Error while finding product by name", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to find product by name",
                    e.getMessage()
            );
        }
    }

    public List<Products> priceProduct(BigDecimal price) {
        try {
            List<Products> products = productRepository.findProductsUnderPriceDesc(price);
            if (products.isEmpty()) {
                throw new SYMException(
                        HttpStatus.NOT_FOUND,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                        String.format("No products found under price: %s", price),
                        String.format("No products found in DB under price: %s", price)
                );
            }
            return products;
        } catch (SYMException e) {
            throw e; // already custom handled above
        } catch (Exception e) {
            logger.error("Error while fetching products under price", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to fetch products by price",
                    e.getMessage()
            );
        }
    }

    public List<Products> getCategoryProduct(String category) {
        try {
            List<Products> products = productRepository.findProductByCategory(category);
            if (products.isEmpty()) {
                throw new SYMException(
                        HttpStatus.NOT_FOUND,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                        String.format("No products found under category: %s", category),
                        String.format("No products found in DB under category: %s", category)
                );
            }
            return products;
        } catch (SYMException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching products by category", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to fetch products by category",
                    e.getMessage()
            );
        }
    }

    public List<Products> getGenderProduct(String gender) {
        try {
            List<Products> products = productRepository.findProductByGender(gender);
            if (products.isEmpty()) {
                throw new SYMException(
                        HttpStatus.NOT_FOUND,
                        SYMErrorType.GENERIC_EXCEPTION,
                        ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                        String.format("No products found for gender: %s", gender),
                        String.format("No products found in DB for gender: %s", gender)
                );
            }
            return products;
        } catch (SYMException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching products by gender", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_AUTHZ_UNKNOWN,
                    "Failed to fetch products by gender",
                    e.getMessage()
            );
        }
    }

    public List<Products> getAllProduct() {
        try {
            List<Products> products = productRepository.getAllProducts();
            if (products == null || products.isEmpty()) {
                return Collections.emptyList();
            }
            return products;
        } catch (Exception e) {
            logger.error("Error while fetching all products", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                    "Failed to fetch all products",
                    e.getMessage()
            );
        }
    }


    public List<Products> findProductsByFilters(String category, Double minPrice, Double maxPrice,
                                                Double minWeight, Double maxWeight, String materialType,
                                                String gender, Boolean isAvailable, Integer minAvailableStock,
                                                Integer maxAvailableStock) {
        try {
            return productRepository.findProductsByFilters(category, minPrice, maxPrice, minWeight, maxWeight,
                    materialType, gender, isAvailable, minAvailableStock, maxAvailableStock);
        } catch (Exception e) {
            logger.error("Error while filtering products", e);
            throw new SYMException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SYMErrorType.GENERIC_EXCEPTION,
                    ErrorCodeConstants.ERROR_CODE_PRODUCT_NOT_FOUND,
                    "Failed to fetch filtered products",
                    e.getMessage()
            );
        }
    }
}
