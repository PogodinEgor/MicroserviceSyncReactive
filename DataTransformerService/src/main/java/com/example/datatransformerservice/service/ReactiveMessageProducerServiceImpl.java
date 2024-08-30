package com.example.datatransformerservice.service;

import com.example.datatransformerservice.configuration.RabbitConfiguration;
import com.example.datatransformerservice.dto.ReactiveMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReactiveMessageProducerServiceImpl {
    private final RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = RabbitConfiguration.REACTIVE_QUEUE_REQUEST)
    public void receiveMessages(ReactiveMessageDTO reactiveMessageDTO) {
        reactiveMessageDTO.setProcessedTime(LocalDateTime.now());
        reactiveMessageDTO.setReactActive(true);
        rabbitTemplate.convertAndSend(RabbitConfiguration.REACTIVE_QUEUE_RESPONSE, reactiveMessageDTO);
    }
}
