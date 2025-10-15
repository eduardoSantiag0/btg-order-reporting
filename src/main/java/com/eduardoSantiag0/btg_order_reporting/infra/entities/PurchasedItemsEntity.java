package com.eduardoSantiag0.btg_order_reporting.infra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchased_items")
public class PurchasedItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_item_id")
    private Long purchasedItemId ;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public PurchasedItemsEntity(OrderEntity order, String productName, int quantity, BigDecimal price) {
        this.order = order;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

}
