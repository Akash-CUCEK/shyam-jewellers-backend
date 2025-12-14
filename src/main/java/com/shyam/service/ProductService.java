package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import jakarta.validation.Valid;

public interface ProductService {

    ProductAddResponseDTO addProduct(ProductAddRequestDTO productAddRequestDTO, String requestId);

    UpdateResponseDTO updateProduct(UpdateRequestDTO updateRequestDTO, String requestId);

    DeleteResponseDTO deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO, String requestId);

    GetProductResponseDTO getPriceProduct(PriceRequestDTO priceRequestDTO, String requestId);

    CategoryResponseDTO getCategoryProduct(CategoryRequestDTO categoryRequestDTO, String requestId);

    GenderResponseDTO getGenderProduct(GenderRequestDTO genderRequestDTO, String requestId);

    ProductResponseDTO getNameProduct(@Valid GetProductByNameRequestDTO getProductByNameRequestDTO, String requestId);

    GetProductResponseDTO getAllProduct(String requestId);

    ProductFilterResponseDTO getFilteredProducts(@Valid ProductFilterRequestDTO filterDTO, String requestId);
}
