package com.eduardoSantiag0.btg_order_reporting.application.errors;

public class ZeroOrNegativeNumberException extends InvalidOrderMessageException {
    public ZeroOrNegativeNumberException(String message) {
        super(message);
    }
}
