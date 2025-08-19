package com.example.Contractor.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides means for serializing objects to JSON format.
 */
public final class JsonUtil {

    private JsonUtil() {}

    /**
     * Serializes passed object to JSON format.
     *
     * @param object
     * @return serialized object as byte array
     */
    public static byte[] serialize(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

}
