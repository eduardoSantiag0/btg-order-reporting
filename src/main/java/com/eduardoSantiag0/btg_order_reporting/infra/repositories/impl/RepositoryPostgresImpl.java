package com.eduardoSantiag0.btg_order_reporting.infra.repositories.impl;

import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.jpa.OrderRepositoryJpa;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.jpa.PurchasedItemsJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RepositoryPostgresImpl implements IRepository {

    private final OrderRepositoryJpa orderRepository;
    private final PurchasedItemsJpaRepository purchasedItemsRepository;

    public RepositoryPostgresImpl(OrderRepositoryJpa orderRepository,
                                  PurchasedItemsJpaRepository purchasedItemsRepository) {
        this.orderRepository = orderRepository;
        this.purchasedItemsRepository = purchasedItemsRepository;
    }


    @Override
    public void savePurchasedItems(List<PurchasedItemsEntity> purchasedItemsEntity) {
        for (var item : purchasedItemsEntity) {
            purchasedItemsRepository.save(item);
        }
    }

    @Override
    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    @Override
    public Optional<OrderEntity> findByOrderId(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public int getNumberOfOrdersByCustomer(Long id) {
        return orderRepository.countByCustomerId(id);
    }

    @Override
    public List<OrderEntity> getAllOrderByCustomerId(Long id) {
        return orderRepository.findAllByCustomerId(id);
    }
}
