package com.example.auth.services.product.impl;

import com.example.auth.domain.product.Product;
import com.example.auth.domain.product.ProductRequestDTO;
import com.example.auth.domain.product.ProductResponseDTO;
import com.example.auth.repositories.ProductRepository;
import com.example.auth.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository repository;
    @Override
    public void createProduct(ProductRequestDTO body) {
        Product newProduct = new Product(body);
        repository.save(newProduct);
    }
    @Override
    public List<ProductResponseDTO> listOfAllProducts() {
        return repository.findAll().stream().map(ProductResponseDTO::new).toList();
    }
}
