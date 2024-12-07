package ru.noname.company.tests;

import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.noname.company.config.AppConfig;
import ru.noname.company.enums.TodoBodyParam;
import ru.noname.company.steps.Steps;

import java.util.EnumMap;
import java.util.Map;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = {AppConfig.class})
public class BaseTest {

    @Setter(onMethod = @__(@Autowired))
    protected Steps steps;

    protected Map<TodoBodyParam, Object> body;

    @BeforeEach
    public void setUp() {
        body = new EnumMap<>(TodoBodyParam.class);
    }
}
