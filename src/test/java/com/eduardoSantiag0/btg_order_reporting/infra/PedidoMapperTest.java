package com.eduardoSantiag0.btg_order_reporting.infra;

import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.application.services.PedidoMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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

}