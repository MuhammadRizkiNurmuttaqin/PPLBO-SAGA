package com.javatechie.product_query_service.repository;

import com.javatechie.product_query_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
