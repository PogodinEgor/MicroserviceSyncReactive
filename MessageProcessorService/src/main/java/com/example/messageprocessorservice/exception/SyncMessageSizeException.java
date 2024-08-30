package com.example.messageprocessorservice.exception;

public class SyncMessageSizeException extends RuntimeException {
    public SyncMessageSizeException(String message) {
        super(message);
    }
}
