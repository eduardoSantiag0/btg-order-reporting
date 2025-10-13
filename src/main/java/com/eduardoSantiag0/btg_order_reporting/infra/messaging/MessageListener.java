package com.eduardoSantiag0.btg_order_reporting.infra.messaging;

import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.services.FailedOrdersHandler;
import com.eduardoSantiag0.btg_order_reporting.services.OrderProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private OrderProcessor orderProcessor;

    @Autowired
    private FailedOrdersHandler failedOrdersHandler;

    @RabbitListener(queues = "order-to-report")
    public void listen(@Payload OrderMessage order) {
        if (!orderProcessor.execute(order)) {
            failedOrdersHandler.fix(order);
        }
    }
}
