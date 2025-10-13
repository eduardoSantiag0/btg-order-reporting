package com.eduardoSantiag0.btg_order_reporting.infra.controllers;

import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.PurchasedItemsResponse;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IRepository repository;

//    private IReportGeneratorStrategy reportStrategy;

//
//    private IReportGeneratorStrategy buildReportStrategy() {
//    }

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

//    public ResponseEntity<?> generateCustomerReport(Long id, String format) {
//
//    }



}
