package com.eduardoSantiag0.btg_order_reporting.infra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_value", nullable = false)
    private BigDecimal orderValue;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    //???
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchasedItemsEntity> items;

    public OrderEntity(Long orderId, BigDecimal orderValue, Long customerId) {
        this.orderId = orderId;
        this.orderValue = orderValue;
        this.customerId = customerId;
    }
}
