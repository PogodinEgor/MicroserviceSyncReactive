package com.example.datatransformerservice.service;

import com.example.datatransformerservice.configuration.RabbitConfiguration;
import com.example.datatransformerservice.dto.SyncMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SyncMessageProducerServiceImpl {
    private final RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = RabbitConfiguration.SYNC_QUEUE_REQUEST)
    public void receiveMessages(SyncMessageDTO syncMessageDTO) {
        syncMessageDTO.setProcessedTime(LocalDateTime.now());
        syncMessageDTO.setSyncActive(true);
        rabbitTemplate.convertAndSend(RabbitConfiguration.SYNC_QUEUE_RESPONSE, syncMessageDTO);
    }

}
