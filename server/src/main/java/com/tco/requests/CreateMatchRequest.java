package com.tco.requests;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.*;
import com.google.gson.Gson;

public class CreateMatchRequest extends Request {

    String usernameWhite;
    String usernameBlack;
    int matchID;
    private final transient Logger log = LoggerFactory.getLogger(CreateMatchRequest.class);

    @Override
    public void buildResponse() {
        MatchList.makeMatch(usernameWhite, usernameBlack);
        matchID = MatchList.getLatestMatchID();
    }

  /* Methods for Testing */

    public CreateMatchRequest() {
        this.requestType = "creatematch";
    }
}