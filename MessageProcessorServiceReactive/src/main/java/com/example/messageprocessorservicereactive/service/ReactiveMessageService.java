package com.example.messageprocessorservicereactive.service;

import com.example.messageprocessorservicereactive.dto.ReactMessageDTO;
import com.example.messageprocessorservicereactive.exception.CountErrorException;
import com.example.messageprocessorservicereactive.exception.ReactMessageSizeException;
import com.example.messageprocessorservicereactive.model.ReactiveMessage;
import com.example.messageprocessorservicereactive.repository.ReactiveMessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReactiveMessageService {

    private final ReactiveMessageRepository reactiveMessageRepository;

    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;


    public Mono<Void> generateMessages(Integer count) {
        if(count == null || count <= 0  ) {
          return  Mono.error(new CountErrorException("Количество собщений должно быть больше 0 и не может быть пустым!"));
        }
        Instant start = Instant.now(); // Начало измерения времени
        return Flux.range(0, count)
                .map(i -> {
                    ReactiveMessage reactiveMessage = new ReactiveMessage();
                    reactiveMessage.setMessage("Reactive Message " + i);
                    reactiveMessage.setSentTime(LocalDateTime.now());
                    reactiveMessage.setProcessedTime(null);
                    reactiveMessage.setReactActive(false);
                    return reactiveMessage;
                })
                .buffer(1000) // Буферизуем данные, чтобы сохранять их порциями, что улучшает производительность
                .flatMap(messages -> reactiveMessageRepository.saveAll(messages).then())
                .then(Mono.fromRunnable(() -> {
                    Instant end = Instant.now(); // Конец измерения времени
                    Duration timeElapsed = Duration.between(start, end);
                    System.out.println("Для создания " + count + " сообщений потребовалось: " + timeElapsed.toSeconds() + " секунд");
                }));
    }

    public Flux<ReactiveMessage> getMessages() {
        return  reactiveMessageRepository.findAll()
                .switchIfEmpty(Mono.error(new ReactMessageSizeException("Список не содержит данных!")));
    }

    public Mono<Void> sendMessagesToQueue() {
        return reactiveMessageRepository.findAllByReactActiveFalse()
                .map(this::toDTO)
                .flatMap(messageDTO -> {
                    rabbitTemplate.convertAndSend("reactive_queue_request", messageDTO);
                    return Mono.empty();
                })
                .then();
    }

    @RabbitListener(queues = "reactive_queue_response")
    public void receiveProcessedMessage(ReactMessageDTO messageDTO) {
        ReactiveMessage reactiveMessage = toEntity(messageDTO);
        reactiveMessageRepository.save(reactiveMessage).subscribe();
    }

    private ReactMessageDTO toDTO(ReactiveMessage reactiveMessage) {
        return modelMapper.map(reactiveMessage, ReactMessageDTO.class);
    }

    private ReactiveMessage toEntity(ReactMessageDTO reactMessageDTO) {
        return modelMapper.map(reactMessageDTO, ReactiveMessage.class);
    }

}
