package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMoveRequest {

    private MoveRequest req;

    @BeforeEach
    public void createConfigurationForTestCases() {
        req = new MoveRequest();
        req.buildResponse();
    }

    @Test
    @DisplayName("Request type is \"move\"")
    public void testType() {
        String type = req.getRequestType();
        assertEquals("move", type);
    }
}