package com.example.Contractor.Exception;

/**
 * Used when needs to notify about failed message sending to RabbitMQ.
 */
public class RabbitSendingException extends RuntimeException {

    public RabbitSendingException(String message) {
        super(message);
    }

}
