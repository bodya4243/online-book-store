package com.example.onlinebookstore.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}
