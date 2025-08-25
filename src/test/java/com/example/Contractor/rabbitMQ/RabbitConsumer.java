package com.example.Contractor.rabbitMQ;

import com.example.Contractor.AbstractContainer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitConsumer extends AbstractContainer {

    private final ConnectionFactory factory = new ConnectionFactory();

    @Getter
    private static String receivedMessage;

    @Value("${app.rabbit.queue}")
    private String queue;

    @Value("${app.rabbit.exchange}")
    private String exchange;

    public RabbitConsumer() {
        factory.setHost(System.getProperty("app.rabbit.host"));
        factory.setPort(Integer.parseInt(System.getProperty("app.rabbit.port")));
    }

    public void consume() throws Exception {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        configConsumer(channel);
        channel.basicConsume(queue, false, deliverCallback(), consumerTag -> {});
    }

    private void configConsumer(Channel channel) throws IOException {
        queueDeclare(channel);
        exchangeDeclare(channel);
        queueBind(channel);
    }

    private void queueDeclare(Channel channel) throws IOException {
        channel.queueDeclare(queue, false, false, false, null);
    }

    private void exchangeDeclare(Channel channel) throws IOException {
        channel.exchangeDeclare(exchange, "direct", true);
    }

    private void queueBind(Channel channel) throws IOException {
        channel.queueBind(queue, exchange, queue);
    }

    private DeliverCallback deliverCallback() {
        return ((consumerTag, message) -> {
            receivedMessage = new String(message.getBody());
        });
    }

}
