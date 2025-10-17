package com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces;

import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;

import java.util.List;
import java.util.Optional;

public interface IRepository {
    void savePurchasedItems(List<PurchasedItemsEntity> purchasedItemsEntity);
    void saveOrder(OrderEntity orderEntity);
    Optional<OrderEntity> findByOrderId(Long id);
    int getNumberOfOrdersByCustomer(Long id);
    List<OrderEntity>getAllOrderByCustomerId(Long id);

    boolean existsByOrderId(Long id);
}
