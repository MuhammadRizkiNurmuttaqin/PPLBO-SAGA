package com.javatechie.saga.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.event.OrderStatus;
import com.javatechie.saga.commons.event.PaymentStatus;
import com.javatechie.saga.order.entity.PurchaseOrder;
import com.javatechie.saga.order.repository.OrderRepository;
import com.javatechie.saga.order.service.OrderStatusPublisher;

import jakarta.transaction.Transactional;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
        System.out.println("Finding order with ID: " + id);
        PurchaseOrder order = repository.findById(id).orElse(null);
        if (order != null) {
            System.out.println("Order found with current payment status: " + order.getPaymentStatus());
            consumer.accept(order);
            System.out.println("After consumer processing, payment status: " + order.getPaymentStatus());
            updateOrder(order);
            System.out.println("After updateOrder method, payment status: " + order.getPaymentStatus());
        } else {
            System.out.println("Order with ID " + id + " not found");
        }
    }

    private void updateOrder(PurchaseOrder purchaseOrder){
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if(!isPaymentComplete){
            publisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
        // Tambahkan ini untuk memastikan perubahan disimpan ke database
        repository.save(purchaseOrder);
        System.out.println("Order saved with payment status: " + purchaseOrder.getPaymentStatus());
    }

    public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
