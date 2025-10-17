package com.eduardoSantiag0.btg_order_reporting.messaging.processors;

import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FailedOrdersProcessor {

    private static final String EXCHANGE_NAME = "order.direct";
    private static final String DLQ_ROUTING_KEY = "order-report.dlq";

    private final RabbitTemplate rabbitTemplate;

    public FailedOrdersProcessor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handleError(OrderMessage order, Exception e) {
        try {
            InvalidOrderMessage failedMessage = new InvalidOrderMessage(order,
                    e.getMessage(),
                    e.getClass().getSimpleName(),
                    Instant.now());

            rabbitTemplate.convertAndSend(EXCHANGE_NAME, DLQ_ROUTING_KEY, failedMessage);

        } catch (Exception ex) {
            System.err.println("Failed to send message to DLQ: " + ex.getMessage());
        }
    }


}
