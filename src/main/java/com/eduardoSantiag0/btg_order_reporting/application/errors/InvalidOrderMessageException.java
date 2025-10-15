package com.eduardoSantiag0.btg_order_reporting.application.errors;


public abstract class InvalidOrderMessageException extends RuntimeException {
    public InvalidOrderMessageException(String message) {
        super(message);
    }
}