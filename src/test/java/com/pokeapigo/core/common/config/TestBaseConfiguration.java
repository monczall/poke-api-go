package com.pokeapigo.core.common.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TestBaseConfiguration {

    private static final String PG_IMAGE = "postgres:16.0-alpine3.18";
    private static final String PG_DB = "integration-tests-db";
    private static final String PG_USERNAME = "sa";
    private static final String PG_PASSWORD = "sa";

    @LocalServerPort
    int localPort;

    @BeforeEach
    void init() {
        RestAssured.port = localPort;
    }

    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(PG_IMAGE)
            .withDatabaseName(PG_DB)
            .withUsername(PG_USERNAME)
            .withPassword(PG_PASSWORD)
            .withReuse(true);

    @DynamicPropertySource
    static void springProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
}
