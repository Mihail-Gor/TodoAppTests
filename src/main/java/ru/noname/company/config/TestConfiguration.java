package ru.noname.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.test.properties")
public class TestConfiguration {
    public static String baseURL;
    public static String username;
    public static String password;

    @Value("${stand.base_url}")
    public void setBaseURL(String baseURL) {
        TestConfiguration.baseURL = baseURL;
    }

    @Value("${default.username}")
    public void setUsername(String username) {
        TestConfiguration.username = username;
    }

    @Value("${default.password}")
    public void setPassword(String password) {
        TestConfiguration.password = password;
    }
}
