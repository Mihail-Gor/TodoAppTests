package ru.noname.company.tests;

import javax0.yamaledt.Name;
import javax0.yamaledt.YamlSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.http.HttpStatus;
import ru.noname.company.enums.TodoBodyParam;

import java.util.List;
import java.util.Map;

import static ru.noname.company.enums.TodoBodyParam.*;

public class TodoAppTests extends BaseTest {

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Add TODO\" test suit: ")
    @YamlSource(value = "test_data/add_todo.yaml")
    void addTodoTests(@NotNull @Name("DisplayName") String testName,
                      @Name("id") Integer id,
                      @Name("text") String text,
                      @Name("completed") Boolean completed,
                      @NotNull @Name("random_id") Boolean randomId,
                      @Name("remove_fields") List<String> removeFields,
                      @NotNull @Name("should_be_in_todo_list") Boolean shouldBeInTodoList,
                      @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                      @Name("expected_error_description") String expectedErrorDescription) {

        body = steps.addTodoStep(id, text, completed, randomId, removeFields, expectedStatusCode, expectedErrorDescription);
        steps.checkAppearedTodoStep(body, shouldBeInTodoList, HttpStatus.OK.value());
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Update TODO\" test suit: ")
    @YamlSource(value = "test_data/update_todo.yaml")
    void updateTodoTests(@NotNull @Name("DisplayName") String testName,
                         @Name("id") Integer id,
                         @Name("text") String text,
                         @Name("completed") Boolean completed,
                         @NotNull @Name("random_id") Boolean randomId,
                         @Name("preconditions") List<String> preconditions,
                         @Name("remove_fields") List<String> removeFields,
                         @NotNull @Name("should_be_in_todo_list") Boolean shouldBeInTodoList,
                         @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                         @Name("expected_error_description") String expectedErrorDescription) {

        List<Map<TodoBodyParam, Object>> bodyList = steps.launchPreconditions(preconditions);
        for (Map<TodoBodyParam, Object> body : bodyList) {
            Map<TodoBodyParam, Object> updatedBody = steps.updateTodoStep(id == null ? (Integer) body.get(ID) : id,
                    text, completed, randomId, removeFields, expectedStatusCode, expectedErrorDescription);
            steps.checkAppearedTodoStep(updatedBody, shouldBeInTodoList, HttpStatus.OK.value());
        }
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Delete TODO\" test suit: ")
    @YamlSource(value = "test_data/delete_todo.yaml")
    void deleteTodoTests(@NotNull @Name("DisplayName") String testName,
                         @Name("preconditions") List<String> preconditions,
                         @NotNull @Name("should_be_in_todo_list") Boolean shouldBeInTodoList,
                         @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                         @Name("expected_error_description") String expectedErrorDescription) {

        List<Map<TodoBodyParam, Object>> bodyList = steps.launchPreconditions(preconditions);
        for (Map<TodoBodyParam, Object> body : bodyList) {
            steps.deleteTodoStep(body.get(ID), expectedStatusCode, expectedErrorDescription);
            steps.checkAppearedTodoStep(body, shouldBeInTodoList, HttpStatus.OK.value());
        }
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Get TODO\" test suit: ")
    @YamlSource(value = "test_data/get_todo.yaml")
    void getTodoTests(@NotNull @Name("DisplayName") String testName,
                      @Name("preconditions") List<String> preconditions,
                      @NotNull @Name("expected_status_code") Integer expectedStatusCode) {

        List<Map<TodoBodyParam, Object>> bodyList = steps.launchPreconditions(preconditions);
        steps.getMultipleTodoStep(bodyList, expectedStatusCode);
    }
}
