package com.example.messageprocessorservicereactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageProcessorServiceReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageProcessorServiceReactiveApplication.class, args);
    }

}
