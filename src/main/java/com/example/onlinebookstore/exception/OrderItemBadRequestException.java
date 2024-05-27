package com.example.onlinebookstore.exception;

public class OrderItemBadRequestException extends RuntimeException {
    public OrderItemBadRequestException(String message) {
        super(message);
    }
}
