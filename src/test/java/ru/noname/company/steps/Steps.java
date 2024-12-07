package ru.noname.company.steps;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.noname.company.enums.TodoBodyParams;
import ru.noname.company.service.TodoAppService;
import ru.noname.company.util.TestUtils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@RequiredArgsConstructor
public class Steps {

    private final TodoAppService todoAppService;

    @Step("Step - add TODO")
    public Map<TodoBodyParams, Object> addTodoStep(
            Integer id, String text, Boolean completed, Boolean randomId,
                            List<String> removeFields, Integer expectedStatusCode, String expectedErrorDescription) {
        Map<TodoBodyParams, Object> body = new EnumMap<>(TodoBodyParams.class);
        id = randomId ? Math.abs(new Random().nextInt()) : id;

        if (removeFields == null || !removeFields.contains(TodoBodyParams.ID.getValue()))
            body.put(TodoBodyParams.ID, id);
        if (removeFields == null || !removeFields.contains(TodoBodyParams.TEXT.getValue()))
            body.put(TodoBodyParams.TEXT, text);
        if (removeFields == null || !removeFields.contains(TodoBodyParams.COMPLETED.getValue()))
            body.put(TodoBodyParams.COMPLETED, completed);

        ResponseEntity<String> response = todoAppService.addTodos(body);
        assertEquals(expectedStatusCode, response.getStatusCode().value());

        if (expectedErrorDescription != null) {
            assertThat(response.getBody()).contains(expectedErrorDescription);
        }

        return body;
//        return todoAppService.addTodos(body);
    }

    @Step("Step - update TODO")
    public void updateTodoStep(Map<TodoBodyParams, Object> body, Integer id, Integer expectedStatusCode, String expectedErrorDescription) {
        ResponseEntity<String> response = todoAppService.updateTodos(body, id);
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (expectedErrorDescription != null) {
            assertEquals(expectedErrorDescription, response.getStatusCode().getReasonPhrase());
        }
    }

    @Step("Step - delete TODO")
    public void deleteTodoStep(Integer id, Integer expectedStatusCode, String expectedErrorDescription) {
        ResponseEntity<String> response = todoAppService.deleteTodos(id);
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (expectedErrorDescription != null) {
            assertThat(response.getBody()).contains(expectedErrorDescription);
        }
    }

    @Step("Step - get TODO")
    public void getTodoStep(Map<TodoBodyParams, Object> body, Integer expectedStatusCode, String expectedErrorDescription) {
        ResponseEntity<String> response = todoAppService.getTodos();
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (expectedErrorDescription != null) {
            assertThat(response.getBody()).contains(expectedErrorDescription);
        }
        assertThat(response.getBody()).contains(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
    }

    @Step("Step - check TODO")
    public void checkAppearedTodoStep(Map<TodoBodyParams, Object> body, Boolean shouldBeUpdated, Integer expectedStatusCode) {
        ResponseEntity<String> response = todoAppService.getTodos();
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (shouldBeUpdated)
            assertThat(response.getBody()).contains(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
        else
            assertThat(response.getBody()).doesNotContain(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
    }
}
