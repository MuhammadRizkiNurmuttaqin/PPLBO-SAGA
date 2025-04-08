package com.javatechie.product_query_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.javatechie.dto.ProductEvent;
import com.javatechie.product_query_service.entity.Product;
import com.javatechie.product_query_service.repository.ProductRepository;

@Service
public class ProductQueryService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
    public void processProductEvents(ProductEvent productEvent) {
        System.out.println("Received Product Event: " + productEvent);

        Product product = productEvent.getProduct();
        if ("CreateProduct".equals(productEvent.getEventType())) {
            System.out.println("Saving new product: " + product);
            repository.save(product);
            System.out.println("Product saved successfully!");
        }
        if ("UpdateProduct".equals(productEvent.getEventType())) {
            repository.findById(product.getId()).ifPresent(existingProduct -> {
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDescription(product.getDescription());
                repository.save(existingProduct);
                System.out.println("Product updated successfully!");
            });
        }
    }

}
