package com.eduardoSantiag0.btg_order_reporting.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Item(
        @JsonProperty("produto")
        String productName,
        @JsonProperty("quantidade")
        int quantity,
        @JsonProperty("preco")
        BigDecimal price
) {
}
