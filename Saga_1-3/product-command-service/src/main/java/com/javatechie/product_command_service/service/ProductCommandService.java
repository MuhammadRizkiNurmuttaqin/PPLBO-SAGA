package com.javatechie.product_command_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.javatechie.product_command_service.repository.ProductRepository;
import com.javatechie.dto.ProductEvent;
import com.javatechie.product_command_service.entity.Product;

@Service
public class ProductCommandService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ProductRepository repository;

    public Product createProduct(ProductEvent productEvent) {
        Product productDO = repository.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("CreateProduct", productDO);
        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }

    public Product updateProduct(long id, ProductEvent productecEvent) {
        Product existingProduct = repository.findById(id).get();
        Product newProduct = productecEvent.getProduct();
        existingProduct.setName(newProduct.getName());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setDescription(newProduct.getDescription());
        Product productDO = repository.save(existingProduct);
        ProductEvent event = new ProductEvent("UpdateProduct", productDO);
        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }
    
}
