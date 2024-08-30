package com.example.messageprocessorservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountErrorResponse {
    private String message;
    private Long timestamp;
}
