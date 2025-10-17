package com.eduardoSantiag0.btg_order_reporting.messaging.errors;

public class NoItemsInMessageException extends InvalidOrderMessageException {
    public NoItemsInMessageException(String message) {
        super(message);
    }
}
