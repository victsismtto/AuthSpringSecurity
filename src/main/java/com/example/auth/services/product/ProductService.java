package com.example.auth.services.product;

import com.example.auth.domain.product.ProductRequestDTO;
import com.example.auth.domain.product.ProductResponseDTO;
import com.example.auth.domain.user.AuthenticationDTO;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequestDTO body);

    List<ProductResponseDTO> listOfAllProducts();
}
