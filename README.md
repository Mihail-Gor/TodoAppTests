# TodoAppTests

---

## Test project based on yaml parametrized test for TODO Application

### To expand test coverage - you should only add new test to one of test data files by the path:

````
src/test/resources/ru/noname/company/tests/test_data
````

### Tests will be created automatically according to your test data

---

## Possible Parameters

---

### POST query (add_todo.yaml)

| Parameter                  | Type    | Usage                          |                                                           Description |
|:---------------------------|:--------|:-------------------------------|----------------------------------------------------------------------:|
| DisplayName                | String  | Create TODO without text field |                                                             Test name |
| id                         | Integer | 1                              |                                      Unique identifier of TODO record |
| text                       | String  | Do smth                        |                                              Main text of TODO record |
| completed                  | Boolean | true                           |                                       Progress pointer of TODO record |
| random_id                  | Boolean | true                           |                             Indicates whether the id should be random |
| remove_fields              | List    | [id,text]                      |                    Indicates which fields should be deleted from json |
| should_be_in_todo_list     | Boolean | true                           | Indicates whether TODO record should be recorded after test completed |
| expected_status_code       | Integer | 201                            |                            Expected status code after test completion |
| expected_error_description | String  | Not Found                      |                                  Expected error after test completion |

---

### DELETE query (delete_todo.yaml)

| Parameter                  | Type    | Usage                          |                                                                               Description |
|:---------------------------|:--------|:-------------------------------|------------------------------------------------------------------------------------------:|
| DisplayName                | String  | Create TODO without text field |                                                                                 Test name |
| preconditions              | List    | [add,update,delete]            | Launches default precondition steps (if you need to create todo order before update etc.) |
| should_be_in_todo_list     | Boolean | true                           |                     Indicates whether TODO record should be recorded after test completed |
| expected_status_code       | Integer | 201                            |                                                Expected status code after test completion |
| expected_error_description | String  | Not Found                      |                                                      Expected error after test completion |

---

### GET query (get_todo.yaml)

| Parameter            | Type    | Usage                          |                                                                               Description |
|:---------------------|:--------|:-------------------------------|------------------------------------------------------------------------------------------:|
| DisplayName          | String  | Create TODO without text field |                                                                                 Test name |
| preconditions        | List    | [add,update,delete]            | Launches default precondition steps (if you need to create todo order before update etc.) |
| expected_status_code | Integer | 201                            |                                                Expected status code after test completion |

---

### PUT query (update_todo.yaml)

| Parameter                  | Type    | Usage                          |                                                                               Description |
|:---------------------------|:--------|:-------------------------------|------------------------------------------------------------------------------------------:|
| DisplayName                | String  | Create TODO without text field |                                                                                 Test name |
| id                         | Integer | 1                              |                                                          Unique identifier of TODO record |
| text                       | String  | Do smth                        |                                                                  Main text of TODO record |
| completed                  | Boolean | true                           |                                                           Progress pointer of TODO record |
| random_id                  | Boolean | true                           |                                                 Indicates whether the id should be random |
| preconditions              | List    | [add,update,delete]            | Launches default precondition steps (if you need to create todo order before update etc.) |
| remove_fields              | List    | [id,text]                      |                                        Indicates which fields should be deleted from json |
| should_be_in_todo_list     | Boolean | true                           |                     Indicates whether TODO record should be recorded after test completed |
| expected_status_code       | Integer | 201                            |                                                Expected status code after test completion |
| expected_error_description | String  | Not Found                      |                                                      Expected error after test completion |


---

---

### [Attached Test Cases](docs/TestCases.md)