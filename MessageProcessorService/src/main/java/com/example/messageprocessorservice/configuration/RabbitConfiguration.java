package com.example.messageprocessorservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String SYNC_QUEUE_REQUEST = "sync_queue_request";
    public static final String SYNC_QUEUE_RESPONSE = "sync_queue_response";

    public static final String REACTIVE_QUEUE_REQUEST = "reactive_queue_request";

    public static final String REACTIVE_QUEUE_RESPONSE = "reactive_queue_response";


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue syncMessageQueueRequest() {
        return new Queue(SYNC_QUEUE_REQUEST);
    }

    @Bean
    public Queue syncMessageQueueResponse() {
        return new Queue(SYNC_QUEUE_RESPONSE);
    }

    @Bean
    public Queue reactiveMessageQueueRequest() {
        return new Queue(REACTIVE_QUEUE_REQUEST);
    }

    @Bean
    public Queue reactiveMessageQueueResponse() {
        return new Queue(REACTIVE_QUEUE_RESPONSE);
    }
}
