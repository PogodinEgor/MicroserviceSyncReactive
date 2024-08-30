package com.example.messageprocessorservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    private Long id;

    private String message;

    private LocalDateTime sentTime;

    private LocalDateTime processedTime;

    private boolean syncActive;
}
