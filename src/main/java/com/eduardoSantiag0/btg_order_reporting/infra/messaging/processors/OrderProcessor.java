package com.eduardoSantiag0.btg_order_reporting.infra.messaging.processors;

import com.eduardoSantiag0.btg_order_reporting.application.errors.*;
import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.infra.messaging.processors.logging.LogWriter;
import com.eduardoSantiag0.btg_order_reporting.infra.repositories.interfaces.IRepository;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.OrderEntity;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import com.eduardoSantiag0.btg_order_reporting.application.services.PedidoMapper;
import com.eduardoSantiag0.btg_order_reporting.infra.entities.PurchasedItemsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderProcessor {

    private final  IRepository repository;

    private final PedidoMapper mapper;

    private final FailedOrdersProcessor failedOrdersProcessor;

    private final LogWriter logWriter;

    public OrderProcessor(IRepository repository, PedidoMapper mapper, FailedOrdersProcessor
            failedOrdersProcessor, LogWriter logWriter) {
        this.repository = repository;
        this.mapper = mapper;
        this.failedOrdersProcessor = failedOrdersProcessor;
        this.logWriter = logWriter;
    }

    private void validadeOrder(OrderMessage order) {
        if (order.orderId() == null || order.orderId() <= 0) {
            throw new ZeroOrNegativeNumberException("Order code must be positive and non-zero");
        }

        if (order.items() == null || order.items().length == 0) {
            throw new NoItemsInMessageException("Order must contain at least one item");
        }

        for (Item item : order.items()) {
            if (item.quantity() <= 0) {
                throw new ZeroOrNegativeNumberException("Item quantity must be positive");
            }

            if (item.productName() == null || item.productName().isBlank()) {
                throw new InvalidProductNameException("Product name cannot be empty");
            }
        }

        if (repository.existsByOrderCode(order.orderId())) {
            throw new OrderIdAlreadyExistsException("Order ID already exists: " + order.orderId());
        }
    }

    @Transactional
    public void execute(OrderMessage order) {
        try {
            validadeOrder(order);
            System.out.println("\nVALID\n");

            OrderEntity orderEntity = mapper.orderToEntity(order);
            List<PurchasedItemsEntity> purchasedItemsEntity = mapper.purchasedItemsToEntity(order, orderEntity);

            orderEntity.setItems(purchasedItemsEntity);
            repository.saveOrder(orderEntity);

            logWriter.logValidOrder(order);

        } catch (InvalidOrderMessageException ex) {
            System.out.println("INVALID");
            failedOrdersProcessor.handleError(order, ex);
            logWriter.logInvalidOrder(order, ex);
        }

    }



}
