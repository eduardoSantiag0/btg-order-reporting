package com.eduardoSantiag0.btg_order_reporting.domain;

import java.math.BigDecimal;

public record Item(
        String productName,
        int quantity,
        BigDecimal price
) {
}
