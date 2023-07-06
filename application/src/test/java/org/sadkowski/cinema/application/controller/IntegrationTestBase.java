package org.sadkowski.cinema.application.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

}
