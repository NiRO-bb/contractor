package com.example.Contractor.rabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Releases message sending to RabbitMQ queue.
 */
@Component
public class RabbitProducer {

    private final ConnectionFactory factory = new ConnectionFactory();

    private final long timeout = 1000;

    @Value("${app.rabbit.exchange}")
    private String exchange;

    @Value("${app.rabbit.queue}")
    private String queue;

    public RabbitProducer(@Value("${app.rabbit.host}") String host,
                          @Value("${app.rabbit.port}") int port) {
        factory.setHost(host);
        factory.setPort(port);
    }

    /**
     * Sends message to RabbitMQ and return confirmation of message receiving.
     *
     * @param message message must be sent.
     * @return confirmation of message receiving (true - message was received, otherwise - false)
     */
    public boolean send(String message) throws IOException, TimeoutException, InterruptedException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            configProducer(channel);
            AMQP.BasicProperties props = new AMQP.BasicProperties().builder().timestamp(new Date()).build();
            channel.basicPublish(exchange, queue, props, message.getBytes());
            return channel.waitForConfirms(timeout);
        }
    }

    /**
     * Sets some configurations of producer.
     *
     * @param channel
     */
    private void configProducer(Channel channel) throws IOException {
        channel.confirmSelect();
        exchangeDeclare(channel);
        queueBind(channel);
    }

    /**
     * Declares exchange in RabbitMQ (creates if not exists, otherwise does nothing).
     *
     * @param channel
     */
    private void exchangeDeclare(Channel channel) throws IOException {
        channel.exchangeDeclare(exchange, "direct", true);
    }

    /**
     * Binds existing queue with exchange.
     *
     * @param channel
     */
    private void queueBind(Channel channel) throws IOException {
        channel.queueBind(queue, exchange, queue);
    }

}
