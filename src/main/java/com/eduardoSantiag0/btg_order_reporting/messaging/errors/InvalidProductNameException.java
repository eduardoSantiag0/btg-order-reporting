package com.eduardoSantiag0.btg_order_reporting.messaging.errors;

public class InvalidProductNameException extends InvalidOrderMessageException {
    public InvalidProductNameException(String message) {
        super(message);
    }
}
