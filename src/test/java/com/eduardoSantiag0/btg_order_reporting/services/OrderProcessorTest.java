package com.eduardoSantiag0.btg_order_reporting.services;

import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.infra.PedidoMapper;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {


    @Mock
    private IRepository repository;

    @Mock
    private PedidoMapper mapper;

    @InjectMocks
    private OrderProcessor orderProcessor;

    private OrderMessage orderMessage;
    private PurchasedItemsEntity item1;
    private PurchasedItemsEntity item2;
    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        Item[] items = List.of(
                new Item("lapis", 10, new BigDecimal("1.00")),
                new Item("caderno", 10, new BigDecimal("1.00"))
        ).toArray(new Item[0]);

        this.orderMessage = new OrderMessage(1001L, 1L, items);
        this.orderEntity = new OrderEntity(orderMessage.orderCode(), new BigDecimal("20"), orderMessage.clienteCode());

        this.item1 = new PurchasedItemsEntity(orderEntity, items[0].productName(),items[0].quantity(), items[0].price());
        this.item2 = new PurchasedItemsEntity(orderEntity, items[1].productName(),items[1].quantity(), items[1].price());

    }

    @Test
    void shouldSaveInBothRepositories() {
        when(mapper.orderToEntity(orderMessage)).thenReturn(orderEntity);
        when(mapper.purchasedItemsToEntity(orderMessage, orderEntity)).thenReturn(List.of(item1, item2));

        orderProcessor.execute(orderMessage);

        verify(repository, times(1)).saveOrder(orderEntity);
        verify(repository, times(1)).savePurchasedItems(List.of(item1, item2));

    }

    @Test
    void shouldSaveCorrectOrderValueWhenProcessingOrder() {
        BigDecimal expectedValue = new BigDecimal("20");

        when(mapper.orderToEntity(orderMessage)).thenReturn(orderEntity);
        when(mapper.purchasedItemsToEntity(orderMessage, orderEntity)).thenReturn(List.of(item1, item2));
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);

        orderProcessor.execute(orderMessage);

        verify(repository).saveOrder(captor.capture());

        OrderEntity savedOrder = captor.getValue();

        System.out.println(savedOrder.getOrderValue());

        assertEquals(0, expectedValue.compareTo(savedOrder.getOrderValue()));

    }

    //todo Items sem item
    //todo Mapper falhou


}