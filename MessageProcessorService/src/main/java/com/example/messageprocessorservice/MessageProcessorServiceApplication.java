package com.example.messageprocessorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageProcessorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageProcessorServiceApplication.class, args);
    }

}
