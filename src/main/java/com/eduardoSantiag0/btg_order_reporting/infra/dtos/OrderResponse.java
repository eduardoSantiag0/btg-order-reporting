package com.eduardoSantiag0.btg_order_reporting.infra.dtos;

import com.eduardoSantiag0.btg_order_reporting.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse (
        BigDecimal orderValue,
        List<PurchasedItemsResponse> items
){
}
