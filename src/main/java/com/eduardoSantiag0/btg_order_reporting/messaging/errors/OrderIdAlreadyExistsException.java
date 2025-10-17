package com.eduardoSantiag0.btg_order_reporting.messaging.errors;

public class OrderIdAlreadyExistsException extends InvalidOrderMessageException{
    public OrderIdAlreadyExistsException(String message) {
        super(message);
    }
}
