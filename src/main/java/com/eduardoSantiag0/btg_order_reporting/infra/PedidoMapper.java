package com.eduardoSantiag0.btg_order_reporting.infra;

import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {

    public OrderEntity orderToEntity(OrderMessage orderMessage) {

        BigDecimal value = BigDecimal.ZERO;
        for (var i : orderMessage.items()) {
            value = value.add(i.price());
        }

        return new OrderEntity(orderMessage.orderCode(), value,
                orderMessage.clienteCode());
    }

    public List<PurchasedItemsEntity> purchasedItemsToEntity(OrderMessage orderMessage, OrderEntity entity) {

        List<PurchasedItemsEntity> purchasedItems = new ArrayList<>();
        for (var i : orderMessage.items()) {
            purchasedItems.add(new PurchasedItemsEntity
                    (entity, i.productName(), i.quantity(), i.price())
            );
        }

        return purchasedItems;
    }


}
