package com.eduardoSantiag0.btg_order_reporting.messaging.errors;

public class ZeroOrNegativeNumberException extends InvalidOrderMessageException {
    public ZeroOrNegativeNumberException(String message) {
        super(message);
    }
}
