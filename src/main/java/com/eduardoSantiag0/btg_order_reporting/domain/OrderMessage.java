package com.eduardoSantiag0.btg_order_reporting.domain;

public record OrderMessage(
        Long orderCode,
        Long clienteCode,
        Item[] items
) {
}
