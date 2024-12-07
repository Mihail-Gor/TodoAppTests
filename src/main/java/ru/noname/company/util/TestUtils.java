package ru.noname.company.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.noname.company.enums.TodoBodyParams;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestUtils {
    public static String getJsonString(Map<String, Object> body) {
        ObjectMapper objectMapper = new ObjectMapper();
        String bodyAsJsonString;

        try {
            bodyAsJsonString = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return bodyAsJsonString;
    }

    public static Map<String, Object> updateMapKeysToString(Map<TodoBodyParams, Object> body) {
        Map<String, Object> resultedMap = new LinkedHashMap<>();
        body.forEach((todoBodyParams, value) -> resultedMap.put(todoBodyParams.getValue(), value));

        return resultedMap;
    }
}
