package com.eduardoSantiag0.btg_order_reporting.application.services;

import com.eduardoSantiag0.btg_order_reporting.application.services.report.EReportFormat;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.PurchasedItemsResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.ReportInformation;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.ReportStrategyFactory;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.IReportGeneratorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IRepository repository;

    @Autowired
    private ReportStrategyFactory strategyFactory;


    public BigDecimal getTotalOrderValue(Long orderId) {
        Optional<OrderEntity> entity = repository.findByOrderId(orderId);

        if (entity.isPresent()) {
            return entity.get().getOrderValue();
        }

        return BigDecimal.ZERO;
    }

    public Integer getNumberOfOrdersByCustomer(Long id) {
        return repository.getNumberOfOrdersByCustomer(id);
    }

    public List<OrderResponse> getAllOrdersByCustomer(Long id) {
        var orderEntities = repository.getAllOrderByCustomerId(id);

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
    }

    public String generateCustomerReport(Long id, EReportFormat format) {

        int numberOfOrders = this.getNumberOfOrdersByCustomer(id);
        List<OrderResponse> orders = this.getAllOrdersByCustomer(id);

        BigDecimal totalValueSpent = orders.stream()
                .map(OrderResponse::orderValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReportInformation reportInformation = new ReportInformation(numberOfOrders, orders, totalValueSpent);

        IReportGeneratorStrategy strategy = strategyFactory.getStrategy(format);

        return strategy.generateReport(reportInformation);

    }
}
