package com.example.messageprocessorservice.service;


import com.example.messageprocessorservice.configuration.RabbitConfiguration;
import com.example.messageprocessorservice.dto.MessageDTO;
import com.example.messageprocessorservice.exception.CountErrorException;
import com.example.messageprocessorservice.exception.SyncMessageSizeException;
import com.example.messageprocessorservice.model.SyncMessage;
import com.example.messageprocessorservice.repository.SyncMessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SyncMessageService {
    private final SyncMessageRepository syncMessageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ModelMapper modelMapper;

    @Transactional
    public void generateMessage(Integer count) {
        if(count == null || count <= 0){
            throw new CountErrorException("Количество собщений должно быть больше 0 и не может быть пустым!");
        }
        List<SyncMessage> messageList = new ArrayList<>(count);
        Instant start = Instant.now();
        for (int i = 0; i < count; i++) {
            SyncMessage message = new SyncMessage();
            message.setMessage("Message " + i);
            message.setSentTime(LocalDateTime.now());
            message.setProcessedTime(null);
            message.setSyncActive(false);
            messageList.add(message);
        }
        syncMessageRepository.saveAll(messageList);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Для создания " + count + " сообщений потребовалось: " + timeElapsed.toSeconds() + " секунд");
    }

    public List<SyncMessage> getMessages() {
        List<SyncMessage> syncMessages = syncMessageRepository.findAll();
        if(syncMessages.size() == 0){
            throw new SyncMessageSizeException("Список не содержит данных!");
        }
        return syncMessages;
    }

    public void sendMessagesToQueue() {
        List<SyncMessage> syncMessages = syncMessageRepository.findAllBySyncActiveFalse();
        syncMessages.parallelStream()
                .map(this::toDTO)
                .forEach(messageDTO -> rabbitTemplate.convertAndSend(RabbitConfiguration.SYNC_QUEUE_REQUEST, messageDTO));
    }

    @RabbitListener(queues = RabbitConfiguration.SYNC_QUEUE_RESPONSE)
    @Transactional
    public void receiveProcessedMessage(MessageDTO messageDTO) {
        SyncMessage syncMessage = toEntity(messageDTO);
        syncMessageRepository.save(syncMessage);
    }

    private MessageDTO toDTO(SyncMessage syncMessage) {
        return modelMapper.map(syncMessage, MessageDTO.class);
    }

    private SyncMessage toEntity(MessageDTO messageDTO) {
        return modelMapper.map(messageDTO, SyncMessage.class);
    }
}
