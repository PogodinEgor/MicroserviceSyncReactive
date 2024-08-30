package com.example.messageprocessorservicereactive.controller;

import com.example.messageprocessorservicereactive.exception.CountErrorException;
import com.example.messageprocessorservicereactive.exception.ReactMessageSizeException;
import com.example.messageprocessorservicereactive.model.ReactMessageResponse;
import com.example.messageprocessorservicereactive.model.ReactiveMessage;
import com.example.messageprocessorservicereactive.service.ReactiveMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/react")
@RequiredArgsConstructor
public class ReactiveMessageController {

    private final ReactiveMessageService reactiveMessageService;

    @PostMapping("/generate")
    public Mono<ResponseEntity<ReactMessageResponse>> generateMessage(@RequestParam(required = false) Integer count){
        return reactiveMessageService.generateMessages(count)
                .then(Mono.just(ResponseEntity.ok(new ReactMessageResponse("Сообщения успешно сгенерированны!"))))
                .onErrorResume(CountErrorException.class, e ->
                        Mono.just(ResponseEntity.badRequest().body(new ReactMessageResponse(e.getMessage()))));
    }

    @PatchMapping("/send")
    public Mono<Void> sendMessagesToQueue(){
        return reactiveMessageService.sendMessagesToQueue();
    }

    @GetMapping("getAll")
    public Mono<ResponseEntity<?>> getAllMessages() {
        return reactiveMessageService.getMessages()
                .collectList()
                .flatMap(messages -> {
                    if (messages.isEmpty()) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(messages)));
                    }
                })
                .onErrorResume(ReactMessageSizeException.class, ex ->
                        Mono.just(ResponseEntity.noContent().build())
                );
    }
}