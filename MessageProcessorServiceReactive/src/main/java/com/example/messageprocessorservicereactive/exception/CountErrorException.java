package com.example.messageprocessorservicereactive.exception;

public class CountErrorException extends RuntimeException {
    public CountErrorException(String message) {
        super(message);
    }
}
