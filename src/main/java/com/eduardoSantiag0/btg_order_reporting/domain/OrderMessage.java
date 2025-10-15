package com.eduardoSantiag0.btg_order_reporting.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderMessage(
        @JsonProperty("codigoPedido")
        Long orderId,

        @JsonProperty("codigoCliente")
        Long customerId,

        @JsonProperty("itens")
        Item[] items
) {
}
