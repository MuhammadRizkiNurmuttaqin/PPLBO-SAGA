package com.javatechie.saga.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javatechie.saga.commons.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {

    @Autowired
    private OrderStatusUpdateHandler handler;

    @Bean
    public Consumer<PaymentEvent> paymentEvenConsumer() {
        return (payment) -> {
            System.out.println("Consuming payment event: " + payment);
            System.out.println("Order ID: " + payment.getPaymentRequestDto().getOrderId());
            System.out.println("Payment Status: " + payment.getPaymentStatus());

            handler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po -> {
                System.out.println("Order found, updating payment status to: " + payment.getPaymentStatus());
                po.setPaymentStatus(payment.getPaymentStatus());
            });
        };
    }
}
