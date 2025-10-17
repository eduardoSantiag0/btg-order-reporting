package com.eduardoSantiag0.btg_order_reporting.application.services;

import com.eduardoSantiag0.btg_order_reporting.application.services.errors.CustomerIdNotFoundException;
import com.eduardoSantiag0.btg_order_reporting.application.services.errors.InvalidFormatException;
import com.eduardoSantiag0.btg_order_reporting.application.services.errors.OrderIdNotFoundException;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.EReportFormat;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.IReportGeneratorStrategy;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.ReportStrategyFactory;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.PurchasedItemsResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.ReportInformation;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IRepository repository;

    @Autowired
    private ReportStrategyFactory strategyFactory;

    public BigDecimal getTotalOrderValue(Long orderId) {
        OrderEntity entity = repository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderIdNotFoundException("Order ID not found: " + orderId));

        return entity.getOrderValue();
    }

    public Integer getNumberOfOrdersByCustomer(Long id) {
        return repository.getNumberOfOrdersByCustomer(id);
    }

    public List<OrderResponse> getAllOrdersByCustomer(Long id) {
        try {
            var orderEntities = repository.getAllOrderByCustomerId(id);

            if (orderEntities == null || orderEntities.isEmpty()) {
                throw new CustomerIdNotFoundException("No orders found for customer ID: " + id);
            }

            return orderEntities.stream()
                    .map(e -> new OrderResponse(
                            e.getOrderValue(),
                            e.getItems().stream()
                                    .map(item -> new PurchasedItemsResponse(
                                            item.getProductName(),
                                            item.getQuantity(),
                                            item.getPrice()
                                    ))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());

        } catch (CustomerIdNotFoundException ex) {
            throw ex;
        }
    }

    public String generateCustomerReport(Long id, EReportFormat format) {
        int numberOfOrders = this.getNumberOfOrdersByCustomer(id);
        List<OrderResponse> orders = this.getAllOrdersByCustomer(id);

        BigDecimal totalValueSpent = orders.stream()
                .map(OrderResponse::orderValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReportInformation reportInformation = new ReportInformation(numberOfOrders, orders, totalValueSpent);
        IReportGeneratorStrategy strategy = strategyFactory.getStrategy(format);

        if (strategy == null) {
            throw new InvalidFormatException("Invalid report format: " + format);
        }

        return strategy.generateReport(reportInformation);

    }
}
