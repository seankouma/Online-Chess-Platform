package com.tco.requests;

import java.util.ArrayList;
import com.tco.misc.SQLDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest extends Request {

    String email;
    String password;
    String success;
    private final transient Logger log = LoggerFactory.getLogger(LoginRequest.class);

    @Override
    public void buildResponse() {
        System.out.println("Email: " + email + "Password: " + password);
        SQLDatabase DB = new SQLDatabase();
        success = DB.ValidateLogin(email, password);
        log.info("buildResponse -> {}", this);
        System.out.println("Email: " + email + " Password: " + password + " Success: " + success);
    }

  /* Methods for Testing */

    // private String loginUser(String email, String password)
    // {
    //     SQLDatabase DB = new SQLDatabase();
    //     return DB.ValidateLogin(email, password);
    // }

    public LoginRequest() {
        this.requestType = "login";
    }
}