package com.eduardoSantiag0.btg_order_reporting.application.services.errors;

public class InvalidFormatException extends RuntimeException{
    public InvalidFormatException(String message) {
        super(message);
    }
}
