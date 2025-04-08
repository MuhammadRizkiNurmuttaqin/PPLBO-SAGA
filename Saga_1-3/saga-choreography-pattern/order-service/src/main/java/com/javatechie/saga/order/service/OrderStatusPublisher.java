package com.javatechie.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.OrderStatus;

@Service
public class OrderStatusPublisher {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final String TOPIC = "order-event"; // Sesuaikan dengan topik Kafka yang digunakan

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        System.out.println("🔥 Mengirim event ke Kafka: " + orderEvent);

        // Mengirim event ke Kafka
        kafkaTemplate.send(TOPIC, orderEvent);
    }
}
