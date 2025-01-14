package org.localhost.pizzeria.nats.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectSerializer {
    static ObjectMapper mapper = new ObjectMapper();

    public static byte[] serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsBytes(object);
    }

    public static <T> T deserialize(byte[] data, Class<T> targetClass) throws IOException {
        return mapper.readValue(data,targetClass);
    }
}
