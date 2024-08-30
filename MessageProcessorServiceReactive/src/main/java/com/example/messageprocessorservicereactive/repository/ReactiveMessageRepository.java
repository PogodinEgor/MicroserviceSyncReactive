package com.example.messageprocessorservicereactive.repository;

import com.example.messageprocessorservicereactive.model.ReactiveMessage;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReactiveMessageRepository extends R2dbcRepository<ReactiveMessage, Long> {
    Flux<ReactiveMessage> findAllByReactActiveFalse();
}
