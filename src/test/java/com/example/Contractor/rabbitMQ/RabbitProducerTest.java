package com.example.Contractor.rabbitMQ;

import com.example.Contractor.AbstractContainer;
import com.example.Contractor.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
public class RabbitProducerTest extends AbstractContainer {

    private final int sleepTime = 2000;

    @Autowired
    private RabbitProducer producer;

    @Autowired
    private RabbitConsumer consumer;

    @Test
    public void testSend() throws Exception {
        String message = "test";
        Assertions.assertTrue(producer.send(message));
        Thread.sleep(sleepTime);
        Assertions.assertEquals(message, consumer.getReceivedMessage());
    }

}
