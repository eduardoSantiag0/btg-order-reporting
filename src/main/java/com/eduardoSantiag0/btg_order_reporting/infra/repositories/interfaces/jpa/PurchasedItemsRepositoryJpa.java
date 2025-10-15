package com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.jpa;

import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedItemsRepositoryJpa extends JpaRepository<PurchasedItemsEntity, Long> {
//    int countByCustomerId(Long customerId);
//    int countByOrder_CustomerId(Long customerId);
}
