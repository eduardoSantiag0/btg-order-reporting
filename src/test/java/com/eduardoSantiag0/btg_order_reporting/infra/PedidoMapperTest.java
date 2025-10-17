package com.eduardoSantiag0.btg_order_reporting.infra;

import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.application.services.PedidoMapper;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoMapperTest {

    private PedidoMapper mapper = new PedidoMapper();

    @Test
    void shouldCalculateTotalValueCorrectly() {
        Item[] items = new Item[]{
                new Item("lapis", 10, new BigDecimal("1.00")),
                new Item("caderno", 10, new BigDecimal("1.00"))
        };

        OrderMessage message = new OrderMessage(1001L, 1L, items);

        OrderEntity entity = mapper.orderToEntity(message);

        assertEquals(new BigDecimal("2.00"), entity.getOrderValue());
    }

    @Test
    void whenOrderMessageHasNoItems_ShouldReturnEmptyList() {
        OrderEntity orderEntity = new OrderEntity(1L, BigDecimal.valueOf(100.0), 1L);
        OrderMessage orderMessage = new OrderMessage(1L, 1L, List.of().toArray(new Item[0]));

        List<PurchasedItemsEntity> purchasedItems = mapper.purchasedItemsToEntity(orderMessage, orderEntity);

        assertNotNull(purchasedItems);
        assertTrue(purchasedItems.isEmpty());
    }

}