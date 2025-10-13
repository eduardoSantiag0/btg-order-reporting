package com.eduardoSantiag0.btg_order_reporting.infra.controllers;

import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{customerId}/orders/count")
    public ResponseEntity<Integer> getNumberOfOrdersByCustomer(@PathVariable(name = "customerId") Long customerId) {
        int count = orderService.getNumberOfOrdersByCustomer(customerId);
        return ResponseEntity.ok(count);

    }


    @GetMapping("/orders/{orderId}/total-value")
    public ResponseEntity<BigDecimal> getTotalOrderValue(@PathVariable(name = "orderId") Long orderId) {
        BigDecimal totalOrderValue =  orderService.getTotalOrderValue(orderId);
        return ResponseEntity.ok(totalOrderValue);
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>>getAllOrdersByCustomer(@PathVariable(name = "customerId")Long customerId) {
        List<OrderResponse> orders = orderService.getAllOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }


}
