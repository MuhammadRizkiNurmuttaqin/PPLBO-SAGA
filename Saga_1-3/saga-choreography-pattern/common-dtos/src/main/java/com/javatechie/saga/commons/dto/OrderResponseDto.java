package com.javatechie.saga.commons.dto;

import com.javatechie.saga.commons.event.OrderStatus;

public class OrderResponseDto {
        
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
    private OrderStatus orderStatus;
}
