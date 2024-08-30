package com.example.messageprocessorservicereactive.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReactMessageResponse {
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime localDateTime;

    public ReactMessageResponse(String message) {
        this.message = message;
        localDateTime = LocalDateTime.now();
    }
}
