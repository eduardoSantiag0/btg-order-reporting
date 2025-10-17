package com.eduardoSantiag0.btg_order_reporting.application.services.errors;

public class CustomerIdNotFoundException extends RuntimeException{
    public CustomerIdNotFoundException(String message) {
        super(message);
    }
}
