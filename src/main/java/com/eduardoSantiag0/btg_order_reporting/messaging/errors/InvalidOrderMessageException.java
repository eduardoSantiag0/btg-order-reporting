package com.eduardoSantiag0.btg_order_reporting.messaging.errors;


public abstract class InvalidOrderMessageException extends RuntimeException {
    public InvalidOrderMessageException(String message) {
        super(message);
    }
}