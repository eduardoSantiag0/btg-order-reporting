package com.eduardoSantiag0.btg_order_reporting.application.services.errors;

public class OrderIdNotFoundException extends RuntimeException {
    public OrderIdNotFoundException(String message) {
        super(message);
    }
}
