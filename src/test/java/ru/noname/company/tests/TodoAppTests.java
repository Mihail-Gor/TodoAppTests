package ru.noname.company.tests;

import javax0.yamaledt.Name;
import javax0.yamaledt.YamlSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import ru.noname.company.enums.TodoBodyParams;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoAppTests extends BaseTest {

//    private Integer id;

    //    @BeforeEach
//    void setId() {
//        id = new Faker().number().numberBetween(0, 999999);
//    }
    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Add TODO\" test suit: ")
    @YamlSource(value = "test_data/add_todo.yaml")
    void addTodoTests(@NotNull @Name("DisplayName") String testName,
                      @Name("id") Integer id,
                      @Name("text") String text,
                      @Name("completed") Boolean completed,
                      @NotNull @Name("random_id") Boolean randomId,
                      @Name("remove_fields") List<String> removeFields,
                      @NotNull @Name("should_be_updated") Boolean shouldBeUpdated,
                      @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                      @Name("expected_error_description") String expectedErrorDescription) {

        Map<TodoBodyParams, Object> body =
                steps.addTodoStep(id, text, completed, randomId, removeFields, expectedStatusCode, expectedErrorDescription);
        steps.checkAppearedTodoStep(body, shouldBeUpdated, 200);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Update TODO\" test suit: ")
    @YamlSource(value = "test_data/update_todo.yaml")
    void updateTodoTests(@NotNull @Name("DisplayName") String testName,
                         @Name("id") Integer id,
                         @Name("text") String text,
                         @Name("completed") Boolean completed,
                         @NotNull @Name("random_id") Boolean randomId,
                         @Name("remove_fields") List<String> removeFields,
                         @NotNull @Name("should_be_updated") Boolean shouldBeUpdated,
                         @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                         @Name("expected_error_description") String expectedErrorDescription) {

        body = steps.addTodoStep(id, text, completed, randomId, removeFields, 201, null);
        steps.updateTodoStep(body, id, expectedStatusCode, expectedErrorDescription);
        steps.checkAppearedTodoStep(body, shouldBeUpdated, 200);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Delete TODO\" test suit: ")
    @YamlSource(value = "test_data/delete_todo.yaml")
    void deleteTodoTests(@NotNull @Name("DisplayName") String testName,
                         @Name("id") Integer id,
                         @Name("text") String text,
                         @Name("completed") Boolean completed,
                         @NotNull @Name("random_id") Boolean randomId,
                         @Name("remove_fields") List<String> removeFields,
                         @NotNull @Name("should_be_updated") Boolean shouldBeUpdated,
                         @NotNull @Name("expected_status_code") Integer expectedStatusCode,
                         @Name("expected_error_description") String expectedErrorDescription) {

        body = steps.addTodoStep(id, text, completed, randomId, removeFields, 201, null);
        steps.deleteTodoStep(id, expectedStatusCode, expectedErrorDescription);
        steps.checkAppearedTodoStep(body, shouldBeUpdated, 200);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("\"Get TODO\" test suit: ")
    @YamlSource(value = "add_todo_test_data/add_todo.yaml")
    void getTodoTests(@NotNull @Name("DisplayName") String testName,
                      @NotNull @Name("expected_status_code") Integer expectedStatusCode) {
//        steps.getTodoStep(body, expectedStatusCode);
    }
}
