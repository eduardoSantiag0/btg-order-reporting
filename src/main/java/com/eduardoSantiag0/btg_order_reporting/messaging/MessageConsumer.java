package com.eduardoSantiag0.btg_order_reporting.messaging;

import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.messaging.processors.OrderProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private OrderProcessor orderProcessor;

    @RabbitListener(queues = "order-to-report")
    public void listen(@Payload OrderMessage order) {
        System.out.println("\n\nRECEBIDO " + order + "\n\n");
        orderProcessor.execute(order);
    }
}
