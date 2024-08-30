package com.example.messageprocessorservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_messages")
@Getter
@Setter
public class SyncMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "processed_time")
    private LocalDateTime processedTime;

    @Column(name = "sync_active")
    private boolean syncActive;
}
