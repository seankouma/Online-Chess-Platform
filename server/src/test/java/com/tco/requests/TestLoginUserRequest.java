package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLoginUserRequest {

    private LoginRequest req;

    @BeforeEach
    public void createConfigurationForTestCases() {
        req = new LoginRequest();
        req.buildResponse();
    }

    @Test
    @DisplayName("Request type is \"login\"")
    public void testType() {
        String type = req.getRequestType();
        assertEquals("login", type);
    }
}