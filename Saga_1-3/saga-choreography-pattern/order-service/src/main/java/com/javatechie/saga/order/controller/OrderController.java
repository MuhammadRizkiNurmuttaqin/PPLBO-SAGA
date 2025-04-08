package com.javatechie.saga.order.controller;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.order.entity.PurchaseOrder;
import com.javatechie.saga.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder creatOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    public List<PurchaseOrder> getOrders() {
        return orderService.getAllOrders();
    }
    
}
