package com.eduardoSantiag0.btg_order_reporting.application.services;

import com.eduardoSantiag0.btg_order_reporting.application.services.errors.CustomerIdNotFoundException;
import com.eduardoSantiag0.btg_order_reporting.application.services.errors.InvalidFormatException;
import com.eduardoSantiag0.btg_order_reporting.application.services.errors.OrderIdNotFoundException;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.EReportFormat;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.IReportGeneratorStrategy;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.ReportStrategyFactory;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IRepository repository;

    @Mock
    private ReportStrategyFactory strategyFactory;

    @Mock
    private IReportGeneratorStrategy strategy;

    @InjectMocks
    private OrderService orderService;

    private OrderEntity orderEntity;

    @BeforeEach
    void setup() {
        this.orderEntity = new OrderEntity(1L,
                new BigDecimal("200"), 2L);
    }

    @Test
    void whenOrderExists_ShouldReturnTotalOrderValue() {

        OrderEntity order = new OrderEntity(1L,BigDecimal.valueOf(200), 2L);
        when(repository.findByOrderId(1L)).thenReturn(Optional.of(order));

        BigDecimal result = orderService.getTotalOrderValue(1L);

        assertEquals(BigDecimal.valueOf(200), result);
        verify(repository).findByOrderId(1L);
    }

    @Test
    void whenOrderNotFound_ShouldThrowException() {
        when(repository.findByOrderId(99L)).thenReturn(Optional.empty());

        assertThrows(OrderIdNotFoundException.class, () -> orderService.getTotalOrderValue(99L));
    }

    @Test
    void whenCustomerExists_ShouldReturnNumberOfOrders() {
        when(repository.getNumberOfOrdersByCustomer(1L)).thenReturn(3);

        int count = orderService.getNumberOfOrdersByCustomer(1L);

        assertEquals(3, count);
    }


    @Test
    void whenCustomerExists_ShouldReturnAllOrdersByCustomer_() {
        OrderEntity order = new OrderEntity(1L,BigDecimal.valueOf(100.0), 2L);

        PurchasedItemsEntity item = new PurchasedItemsEntity(order, "Book", 2, BigDecimal.valueOf(50.0));
        order.setItems(List.of(item));

        when(repository.getAllOrderByCustomerId(1L)).thenReturn(List.of(order));

        List<OrderResponse> result = orderService.getAllOrdersByCustomer(1L);

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100.0), result.get(0).orderValue());
        assertEquals("Book", result.get(0).items().get(0).productName());
    }

    @Test
    void whenCustomerHasNoOrders_ShouldThrowException() {
        when(repository.getAllOrderByCustomerId(1L)).thenReturn(List.of());

        assertThrows(CustomerIdNotFoundException.class, () -> orderService.getAllOrdersByCustomer(1L));
    }

    @Test
    void shouldGenerateReportSuccessfully() {
        when(repository.getNumberOfOrdersByCustomer(1L)).thenReturn(2);
        OrderEntity order = new OrderEntity(1L,BigDecimal.valueOf(2000.0), 2L);
        PurchasedItemsEntity item = new PurchasedItemsEntity(order, "Laptop", 1, BigDecimal.valueOf(2000.0));
        order.setItems(List.of(item));

        when(repository.getAllOrderByCustomerId(1L)).thenReturn(List.of(order));
        when(strategyFactory.getStrategy(EReportFormat.JSON)).thenReturn(strategy);
        when(strategy.generateReport(any())).thenReturn("{ \"report\": \"ok\" }");

        String report = orderService.generateCustomerReport(1L, EReportFormat.JSON);

        assertEquals("{ \"report\": \"ok\" }", report);
    }

    @Test
    void whenStrategyIsNull_ShouldThrowInvalidFormatException() {
        Long customerId = 1L;

        when(repository.getNumberOfOrdersByCustomer(customerId)).thenReturn(1);

        OrderEntity order = new OrderEntity(1L, BigDecimal.valueOf(2000.0), customerId);
        PurchasedItemsEntity item = new PurchasedItemsEntity(order, "Laptop", 1, BigDecimal.valueOf(2000.0));
        order.setItems(List.of(item));
        when(repository.getAllOrderByCustomerId(customerId)).thenReturn(List.of(order));

        when(strategyFactory.getStrategy(EReportFormat.HTML)).thenReturn(null);

        assertThrows(InvalidFormatException.class,
                () -> orderService.generateCustomerReport(customerId, EReportFormat.HTML));
    }
}