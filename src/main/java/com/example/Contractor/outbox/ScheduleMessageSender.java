package com.example.Contractor.outbox;

import com.example.Contractor.DTO.OutboxMessage;
import com.example.Contractor.Exception.RabbitSendingException;
import com.example.Contractor.Service.OutboxService;
import com.example.Contractor.rabbitMQ.RabbitProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Realizes outbox pattern.
 * Polls messages from outbox database table and sends them to RabbitMQ.
 */
@Component
@EnableScheduling
public class ScheduleMessageSender {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private OutboxService service;

    @Autowired
    private RabbitProducer producer;

    /**
     * Periodically polls messages from database and sends to broker.
     */
    @Scheduled(fixedDelayString = "${app.schedule.fixedDelay}",
            initialDelayString = "${app.schedule.initialDelay}")
    @Transactional
    public void pollAndSend() {
        Optional<OutboxMessage> optMessage = service.poll();
        if (optMessage.isPresent()) {
            OutboxMessage message = optMessage.get();
            send(message);
            logger.trace("Message was sent to RabbitMQ - " +
                    "Contractor instance with id = {}", message.getId());
        }
    }

    /**
     * Sends passed message to RabbitMQ.
     * Throw RabbitSendingException if something goes wrong.
     *
     * @param message message must be sent
     */
    private void send(OutboxMessage message) {
        try {
            if (!producer.send(message.getPayload())) {
                throw new RabbitSendingException(
                        "Message sending to RabbitMQ was failed - " +
                                "RabbitMQ not send confirmation of message receiving");
            }
        } catch (IOException | TimeoutException | InterruptedException exception) {
            logger.error("Message sending to RabbitMQ was failed - {}", exception.getMessage());
            throw new RabbitSendingException(exception.getMessage());
        }
    }

}
