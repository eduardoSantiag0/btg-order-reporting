package com.eduardoSantiag0.btg_order_reporting.infra.messaging.processors.logging;

import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;

import java.time.Instant;

public record InvalidOrderLog(
        OrderMessage order,
        String message,
        String errorName,
        Instant timestamp
) {
}
