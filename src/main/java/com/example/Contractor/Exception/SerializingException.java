package com.example.Contractor.Exception;

/**
 * Used when needs to notify about JSON serializing error.
 */
public class SerializingException extends RuntimeException {

    public SerializingException(String message) {
        super(message);
    }

}
