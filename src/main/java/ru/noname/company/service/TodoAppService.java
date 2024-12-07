package ru.noname.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.noname.company.client.RestClient;
import ru.noname.company.enums.TodoBodyParams;

import java.util.Map;

import static ru.noname.company.config.TestConfiguration.*;
import static ru.noname.company.util.TestUtils.updateMapKeysToString;

@Service
@RequiredArgsConstructor
public class TodoAppService {
    private final RestClient restClient;

    public ResponseEntity<String> getTodos() {
        return restClient.getRequest(baseURL);
    }

    public ResponseEntity<String> addTodos(Map<TodoBodyParams, Object> body) {
        return restClient.postRequest(baseURL, updateMapKeysToString(body));
    }

    public ResponseEntity<String> updateTodos(Map<TodoBodyParams, Object> body, Integer id) {
        return restClient.putRequest(String.format("%s/%d", baseURL, id), updateMapKeysToString(body));
    }

    public ResponseEntity<String> deleteTodos(Integer id) {
        return restClient.deleteRequest(String.format("%s/%d", baseURL, id));
    }
}