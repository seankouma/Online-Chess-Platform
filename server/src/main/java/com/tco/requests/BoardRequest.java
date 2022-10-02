package com.tco.requests;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.*;
import com.google.gson.Gson;

public class BoardRequest extends Request {

    int boardID;
    String[][] board;
    private final transient Logger log = LoggerFactory.getLogger(BoardRequest.class);

    @Override
    public void buildResponse() {
        board = MatchList.getMatch(boardID).getBoard().getBoardString();
    }

  /* Methods for Testing */

    public BoardRequest() {
        this.requestType = "board";
    }
}