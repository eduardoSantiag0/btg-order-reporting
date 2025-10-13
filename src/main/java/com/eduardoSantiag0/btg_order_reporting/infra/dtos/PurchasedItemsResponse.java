package com.eduardoSantiag0.btg_order_reporting.infra.dtos;

import java.math.BigDecimal;

public record PurchasedItemsResponse(
        String productName,
        int quantity,
        BigDecimal price
) {
}
