package com.example.messageprocessorservicereactive.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "reactive_messages")
@Getter
@Setter
public class ReactiveMessage {

    @Id
    @Column("id")
    private Long id;

    @Column("message")
    private String message;

    @Column("sent_time")
    private LocalDateTime sentTime;

    @Column("processed_time")
    private LocalDateTime processedTime;

    @Column("react_active")
    private boolean reactActive;
}