package com.tco.requests;

import com.tco.misc.SQLDatabase;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUserRequest extends Request {

    String firstName;
    String lastName;
    String email;
    String username;
    String password;
    boolean success;
    private final transient Logger log = LoggerFactory.getLogger(RegisterUserRequest.class);

    @Override
    public void buildResponse() {
        System.out.println("First: " + firstName + " Last: " + lastName + " Email: " + email + " Username: " + username + "Password: " + password);
        this.success = registerUser();
        log.trace("buildResponse -> {}", this);
    }

    private boolean registerUser() {
        SQLDatabase DB = new SQLDatabase();
        DB.RegisterNewUser(firstName, lastName, email, username, password);
        return true;
    }

    /* Methods for Testing */

    public RegisterUserRequest() {
        this.requestType = "register";
    }
}