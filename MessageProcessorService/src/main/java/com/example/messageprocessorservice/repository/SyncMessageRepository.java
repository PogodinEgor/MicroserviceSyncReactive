package com.example.messageprocessorservice.repository;

import com.example.messageprocessorservice.model.SyncMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyncMessageRepository extends JpaRepository<SyncMessage, Long> {
    List<SyncMessage> findAllBySyncActiveFalse();
}
