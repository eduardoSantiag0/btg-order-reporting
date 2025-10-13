package com.eduardoSantiag0.btg_order_reporting.services;

import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.infra.PedidoMapper;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProcessor {

    @Autowired
    private IRepository repository;

    @Autowired
    private PedidoMapper mapper;

    public boolean execute(OrderMessage order) {

        OrderEntity orderEntity = mapper.orderToEntity(order);

        List<PurchasedItemsEntity> purchasedItemsEntity = mapper.purchasedItemsToEntity(order, orderEntity);

        repository.saveOrder(orderEntity);

        repository.savePurchasedItems(purchasedItemsEntity);

        return true;

    }

}
