package com.tco.requests;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.*;
import com.google.gson.Gson;

public class MatchRequest extends Request {

    ArrayList<MatchInfo> info;
    String username;
    private final transient Logger log = LoggerFactory.getLogger(MatchRequest.class);

    @Override
    public void buildResponse() {
        info = MatchList.getMatchInfo(username);
    }

  /* Methods for Testing */

    public MatchRequest() {
        this.requestType = "match";
    }
}