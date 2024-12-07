package ru.noname.company.steps;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.noname.company.enums.TodoBodyParam;
import ru.noname.company.service.TodoAppService;
import ru.noname.company.util.TestUtils;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.noname.company.enums.TodoBodyParam.*;

@Component
@RequiredArgsConstructor
public class Steps {

    private final TodoAppService todoAppService;

    @Step("Step - add TODO")
    public Map<TodoBodyParam, Object> addTodoStep(
            Integer id, String text, Boolean completed, Boolean randomId,
                            List<String> removeFields, Integer expectedStatusCode, String expectedErrorDescription) {
        Map<TodoBodyParam, Object> body = new EnumMap<>(TodoBodyParam.class);
        id = randomId ? Math.abs(new Random().nextInt()) : id;

        if (removeFields == null || !removeFields.contains(TodoBodyParam.ID.getValue()))
            body.put(TodoBodyParam.ID, id);
        if (removeFields == null || !removeFields.contains(TodoBodyParam.TEXT.getValue()))
            body.put(TodoBodyParam.TEXT, text);
        if (removeFields == null || !removeFields.contains(TodoBodyParam.COMPLETED.getValue()))
            body.put(TodoBodyParam.COMPLETED, completed);

        ResponseEntity<String> response = todoAppService.addTodos(body);
        assertEquals(expectedStatusCode, response.getStatusCode().value());

        if (expectedErrorDescription != null) {
            assertThat(response.getBody()).contains(expectedErrorDescription);
        }

        return body;
    }

    @Step("Step - add default TODO")
    public Map<TodoBodyParam, Object> addDefaultTodoStep() {
        Map<TodoBodyParam, Object> body = new EnumMap<>(TodoBodyParam.class);

        body.put(TodoBodyParam.ID, Math.abs(new Random().nextInt()));
        body.put(TodoBodyParam.TEXT, "default");
        body.put(TodoBodyParam.COMPLETED, true);

        ResponseEntity<String> response = todoAppService.addTodos(body);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());

        return body;
    }

    @Step("Step - update TODO")
    public Map<TodoBodyParam, Object> updateTodoStep(Integer id, String text, Boolean completed, Boolean randomId,
                               List<String> removeFields, Integer expectedStatusCode, String expectedErrorDescription) {

        Map<TodoBodyParam, Object> body = new EnumMap<>(TodoBodyParam.class);
        id = randomId ? Math.abs(new Random().nextInt()) : id;

        if (removeFields == null || !removeFields.contains(TodoBodyParam.ID.getValue()))
            body.put(TodoBodyParam.ID, id);
        if (removeFields == null || !removeFields.contains(TodoBodyParam.TEXT.getValue()))
            body.put(TodoBodyParam.TEXT, text);
        if (removeFields == null || !removeFields.contains(TodoBodyParam.COMPLETED.getValue()))
            body.put(TodoBodyParam.COMPLETED, completed);

        ResponseEntity<String> response = todoAppService.updateTodos(body, (Integer) body.get(ID));
        assertEquals(expectedStatusCode, response.getStatusCode().value());

        if (expectedErrorDescription != null) {
            assertEquals(expectedErrorDescription, response.getStatusCode().getReasonPhrase());
        }

        return body;
    }

    @Step("Step - update default TODO")
    public Map<TodoBodyParam, Object> updateDefaultTodoStep() {
        Map<TodoBodyParam, Object> body = new EnumMap<>(TodoBodyParam.class);

        body.put(TodoBodyParam.ID, Math.abs(new Random().nextInt()));
        body.put(TodoBodyParam.TEXT, "updated text");
        body.put(TodoBodyParam.COMPLETED, false);

        ResponseEntity<String> response = todoAppService.updateTodos(body, (Integer) body.get(ID));
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());

        return body;
    }

    @Step("Step - delete TODO")
    public void deleteTodoStep(Object id, Integer expectedStatusCode, String expectedErrorDescription) {
        ResponseEntity<String> response = todoAppService.deleteTodos(Integer.parseInt(id.toString()));
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (expectedErrorDescription != null) {
            assertThat(response.getBody()).contains(expectedErrorDescription);
        }
    }

    @Step("Step - get TODO")
    public void getMultipleTodoStep(List<Map<TodoBodyParam, Object>> bodyList, Integer expectedStatusCode) {
        ResponseEntity<String> response = todoAppService.getTodos();
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        for (Map<TodoBodyParam, Object> body : bodyList)
            assertThat(response.getBody()).containsOnlyOnce(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
    }

    @Step("Step - check TODO")
    public void checkAppearedTodoStep(Map<TodoBodyParam, Object> body, Boolean shouldBeInTodoList, Integer expectedStatusCode) {
        ResponseEntity<String> response = todoAppService.getTodos();
        assertEquals(expectedStatusCode, response.getStatusCode().value());
        if (shouldBeInTodoList)
            assertThat(response.getBody()).containsOnlyOnce(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
        else
            assertThat(response.getBody()).doesNotContain(TestUtils.getJsonString(TestUtils.updateMapKeysToString(body)));
    }

    @Step("Step - launch precondition steps")
    public List<Map<TodoBodyParam, Object>> launchPreconditions(List<String> preconditions) {
        List<Map<TodoBodyParam, Object>> bodyList = new ArrayList<>();

        if (preconditions != null) {
            for (String precondition : preconditions) {
                switch (precondition) {
                    case "add" -> {
                        Map<TodoBodyParam, Object> body = addDefaultTodoStep();
                        bodyList.add(body);
                        checkAppearedTodoStep(body, true, HttpStatus.OK.value());
                    }
                    case "update" -> {
                        Map<TodoBodyParam, Object> body = addDefaultTodoStep();
                        checkAppearedTodoStep(body, true, HttpStatus.OK.value());
                        body = updateDefaultTodoStep();
                        bodyList.add(body);
                        checkAppearedTodoStep(body, true, HttpStatus.OK.value());
                    }
                    case "delete" -> {
                        Map<TodoBodyParam, Object> body = addDefaultTodoStep();
                        bodyList.add(body);
                        checkAppearedTodoStep(body, true, HttpStatus.OK.value());
                        deleteTodoStep(body.get(ID), HttpStatus.NO_CONTENT.value(), null);
                        checkAppearedTodoStep(body, false, HttpStatus.OK.value());
                    }
                    // TODO - looks like a crutch due to yaml parametrized tests disadvantage, should be added enum somehow
                    default ->
                            throw new IllegalArgumentException("value for precondition should be only: add, update, delete");
                }

            }
        }
        return bodyList;
    }
}
