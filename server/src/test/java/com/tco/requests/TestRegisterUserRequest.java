//package com.tco.requests;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TestRegisterUserRequest {
//
//    private RegisterUserRequest req;
//
//    @BeforeEach
//    public void createConfigurationForTestCases() {
//        req = new RegisterUserRequest();
//        req.buildResponse();
//    }
//
//    @Test
//    @DisplayName("Request type is \"register\"")
//    public void testType() {
//        String type = req.getRequestType();
//        assertEquals("register", type);
//    }
//}