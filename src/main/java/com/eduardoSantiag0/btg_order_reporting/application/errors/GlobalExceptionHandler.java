package com.eduardoSantiag0.btg_order_reporting.application.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ZeroOrNegativeNumberException.class)
    public ResponseEntity<String> handleZeroOrNegativeNumberException
            (ZeroOrNegativeNumberException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());

    }

    @ExceptionHandler(InvalidProductNameException.class)
    public ResponseEntity<String> handleInvalidProductName (InvalidProductNameException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoItemsInMessageException.class)
    public ResponseEntity<String> handleNoItemsInMessageException
            (NoItemsInMessageException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OrderIdAlreadyExistsException.class)
    public ResponseEntity<String> handleOrderIdAlreadyExists
            (OrderIdAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
