package com.example.auth.repositories;

import com.example.auth.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
