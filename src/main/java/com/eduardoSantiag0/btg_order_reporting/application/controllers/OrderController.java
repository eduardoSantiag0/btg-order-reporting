package com.eduardoSantiag0.btg_order_reporting.application.controllers;

import com.eduardoSantiag0.btg_order_reporting.application.services.OrderService;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.OrderResponse;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.EReportFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get the number of orders for a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Number of orders found",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{customerId}/orders/count")
    public ResponseEntity<Integer> getNumberOfOrdersByCustomer(
            @Parameter(description = "Number of orders made by a specific customer", example = "2")
            @PathVariable(name = "customerId") Long customerId) {
        int count = orderService.getNumberOfOrdersByCustomer(customerId);
        return ResponseEntity.ok(count);

    }

    @Operation(summary = "Get total value of a specific order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total order value retrieved",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/orders/{orderId}/total-value")
    public ResponseEntity<BigDecimal> getTotalOrderValue(
            @Parameter(description = "Get total value of a specific order", example = "1012")
            @PathVariable(name = "orderId") Long orderId) {
        BigDecimal totalOrderValue =  orderService.getTotalOrderValue(orderId);
        return ResponseEntity.ok(totalOrderValue);
    }

    @Operation(summary = "Get all orders for a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>>getAllOrdersByCustomer(
            @Parameter(description = "Customer Id", example = "2")
            @PathVariable(name = "customerId") Long customerId
    ) {
        List<OrderResponse> orders = orderService.getAllOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Generate a report for a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report generated successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid report format",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{customerId}/generate-report")
    public ResponseEntity<String> getReport(
            @Parameter(description = "Customer Id", example = "2")
            @PathVariable(name = "customerId") Long customerId,

            @Parameter(description = "Report Format", example = "json")
            @RequestParam String format) {
        String report = orderService.generateCustomerReport(customerId, EReportFormat.valueOf(format.toUpperCase()));
        return ResponseEntity.ok(report);
    }

}
