package com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.jpa;


import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByCustomerId(Long id);
    int countByCustomerId(Long id);
}
