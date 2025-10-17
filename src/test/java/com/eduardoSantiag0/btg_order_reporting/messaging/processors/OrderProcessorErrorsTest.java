package com.eduardoSantiag0.btg_order_reporting.messaging.processors;

import com.eduardoSantiag0.btg_order_reporting.messaging.errors.InvalidProductNameException;
import com.eduardoSantiag0.btg_order_reporting.messaging.errors.NoItemsInMessageException;
import com.eduardoSantiag0.btg_order_reporting.messaging.errors.OrderIdAlreadyExistsException;
import com.eduardoSantiag0.btg_order_reporting.messaging.errors.ZeroOrNegativeNumberException;
import com.eduardoSantiag0.btg_order_reporting.application.services.PedidoMapper;
import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import com.eduardoSantiag0.btg_order_reporting.messaging.processors.logging.LogWriter;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderProcessorErrorsTest {

    @Mock
    private IRepository repository;

    @Mock
    private PedidoMapper mapper;

    @Mock
    private LogWriter logWriter;

    @Mock
    private FailedOrdersProcessor failedOrdersProcessor;

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
        this.orderEntity = new OrderEntity(orderMessage.orderId(), new BigDecimal("20"), orderMessage.customerId());

        this.item1 = new PurchasedItemsEntity(orderEntity, items[0].productName(),items[0].quantity(), items[0].price());
        this.item2 = new PurchasedItemsEntity(orderEntity, items[1].productName(),items[1].quantity(), items[1].price());

    }

    @Test
    void whenZeroOrNegativeQuantity_ShouldThrowException() {
        Item[] itemWithZeroQuantity = { new Item("lapis", 0, BigDecimal.ONE) };
        OrderMessage orderWithZeroQuantity = new OrderMessage(1L, 2L, itemWithZeroQuantity);

        assertThrows(ZeroOrNegativeNumberException.class, () -> orderProcessor.execute(orderWithZeroQuantity));
    }

    @Test
    void whenZeroOrNegativePrice_ShouldThrowException() {
        Item[] itemWithZeroPrice = { new Item("caderno", 10, BigDecimal.ZERO) };
        OrderMessage orderWithZeroPrice = new OrderMessage(1L, 2L, itemWithZeroPrice);

        assertThrows(ZeroOrNegativeNumberException.class, () -> orderProcessor.execute(orderWithZeroPrice));
    }

    @Test
    void whenEmptyItemsList_ShouldThrowException() {
        OrderMessage orderWithEmptyList = new OrderMessage(1L, 2L, new Item[]{});

        assertThrows(NoItemsInMessageException.class, () -> orderProcessor.execute(orderWithEmptyList));
        verify(repository, never()).saveOrder(any());

    }

    @Test
    void whenOrderIdAlreadyExists_ShouldThrowException() {
        when(repository.existsByOrderId(any(Long.class))).thenReturn(true);

        assertThrows(OrderIdAlreadyExistsException.class, () -> orderProcessor.execute(orderMessage));

    }

    @Test
    void whenInvalidProductName_ShouldThrowException() {
        Item[] itemWithInvalidName = { new Item("", 10, BigDecimal.ONE) };
        OrderMessage orderWithInvalidName = new OrderMessage(1L, 2L, itemWithInvalidName);

        assertThrows(InvalidProductNameException.class, () -> orderProcessor.execute(orderWithInvalidName));
    }

    @Test
    void whenNegativeOrNullOrderId_ShouldThorwException() {
        OrderMessage orderWitNullId = new OrderMessage(null, 2L, new Item[]{});
        OrderMessage orderWitNegativeId = new OrderMessage(-1L, 2L, new Item[]{});

        assertThrows(ZeroOrNegativeNumberException.class, () -> orderProcessor.execute(orderWitNullId));
        assertThrows(ZeroOrNegativeNumberException.class, () -> orderProcessor.execute(orderWitNegativeId));

    }

}
