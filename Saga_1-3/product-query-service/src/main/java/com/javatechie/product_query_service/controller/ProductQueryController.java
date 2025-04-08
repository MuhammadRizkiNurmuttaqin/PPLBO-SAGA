package com.javatechie.product_query_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javatechie.product_query_service.service.ProductQueryService;
import com.javatechie.product_query_service.entity.Product;

@RequestMapping("/products")
@RestController
public class ProductQueryController {

    @Autowired
    private ProductQueryService queryService;

    @GetMapping
    public List<Product> fetchAllProducts() {
        List<Product> products = queryService.getProducts();
        System.out.println("Products fetched: " + products.size());
        return products;
    }

}
