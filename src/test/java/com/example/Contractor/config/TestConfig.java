package com.example.Contractor.config;

import com.example.Contractor.rabbitMQ.RabbitConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private RabbitConsumer consumer;

    @PostConstruct
    public void postConstruct() throws Exception {
        consumer.consume();
    }

}
