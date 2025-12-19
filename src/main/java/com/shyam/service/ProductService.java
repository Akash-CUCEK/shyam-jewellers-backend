package com.shyam.service;

import com.shyam.dto.request.*;
import com.shyam.dto.response.*;
import jakarta.validation.Valid;

public interface ProductService {

    ProductAddResponseDTO addProduct(ProductAddRequestDTO productAddRequestDTO );

    UpdateResponseDTO updateProduct(UpdateRequestDTO updateRequestDTO);

    DeleteResponseDTO deleteProduct(DeleteProductRequestDTO deleteProductRequestDTO);

    GetProductResponseDTO getPriceProduct(PriceRequestDTO priceRequestDTO);

    CategoryResponseDTO getCategoryProduct(CategoryRequestDTO categoryRequestDTO);

    GenderResponseDTO getGenderProduct(GenderRequestDTO genderRequestDTO);

    ProductResponseDTO getNameProduct(@Valid GetProductByNameRequestDTO getProductByNameRequestDTO);

    GetProductResponseDTO getAllProduct();

    ProductFilterResponseDTO getFilteredProducts(@Valid ProductFilterRequestDTO filterDTO);
}
