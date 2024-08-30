package com.example.messageprocessorservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SyncMessageResponse {
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime localDateTime;

    public SyncMessageResponse(String message) {
        this.message = message;
        localDateTime = LocalDateTime.now();
    }
}
