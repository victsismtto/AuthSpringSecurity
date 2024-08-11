package com.example.auth.controllers;

import com.example.auth.domain.product.Product;
import com.example.auth.domain.product.ProductRequestDTO;
import com.example.auth.domain.product.ProductResponseDTO;
import com.example.auth.repositories.ProductRepository;
import com.example.auth.services.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("product")
public class ProductController {
    @Autowired private ProductService service;

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody @Valid ProductRequestDTO body, JwtAuthenticationToken token){
        service.createProduct(body);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(service.listOfAllProducts());
    }
}
