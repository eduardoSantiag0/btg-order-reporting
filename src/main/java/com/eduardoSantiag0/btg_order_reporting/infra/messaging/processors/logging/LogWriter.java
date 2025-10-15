package com.eduardoSantiag0.btg_order_reporting.infra.messaging.processors.logging;

import com.eduardoSantiag0.btg_order_reporting.application.errors.InvalidOrderMessageException;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class LogWriter {

    public void logValidOrder(OrderMessage order) {
        log.info("Valid order received: orderId={}, customerId={}, items={}",
                order.orderId(),
                order.customerId(),
                order.items()
        );
    }

    public void logInvalidOrder(OrderMessage order, InvalidOrderMessageException exception) {

        InvalidOrderLog invalidOrder = new InvalidOrderLog(order,
                exception.getMessage(), exception.getClass().getSimpleName(),
                Instant.now());

        log.error("Invalid order: orderId={}, customerId={}, error={}, message={}, timestamp={}",
                invalidOrder.order().orderId(),
                invalidOrder.order().customerId(),
                invalidOrder.errorName(),
                invalidOrder.message(),
                invalidOrder.timestamp()
        );
    }
}
