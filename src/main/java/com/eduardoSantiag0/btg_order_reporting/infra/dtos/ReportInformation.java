package com.eduardoSantiag0.btg_order_reporting.infra.dtos;

import java.math.BigDecimal;
import java.util.List;

public record ReportInformation(
        int numberOfOrders,
        List<OrderResponse> orders,
        BigDecimal totalValueSpent
) {
}
