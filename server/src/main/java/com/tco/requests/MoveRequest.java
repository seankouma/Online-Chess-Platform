package com.tco.requests;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.*;
import com.tco.misc.BoardWebSocketHandler;

public class MoveRequest extends Request {

    int fromRow;
    int fromColumn;
    int toRow;
    int toColumn;
    boolean validMove;
    int boardID;
    String userID;
    private final transient Logger log = LoggerFactory.getLogger(MoveRequest.class);

    @Override
    public void buildResponse() {
        try {
        Match currentMatch = MatchList.getMatch(boardID);
        ChessBoard board = currentMatch.getBoard();
        int[] fromIndices = {fromRow, fromColumn};
        String fromPosition = ChessBoard.indicesToString(fromIndices);
        System.out.println("fromPosition " + fromPosition);
        int[] toIndices = {toRow, toColumn};
        String toPosition = ChessBoard.indicesToString(toIndices);
        System.out.println("toPosition " + toPosition);
        
        System.out.println("Converted back" + Arrays.toString(ChessBoard.getIndices(fromPosition)) + " -> " + Arrays.toString(ChessBoard.getIndices(toPosition)));
        ChessPiece.Color color = MatchList.getMatch(boardID).getColor(userID);
        String colorString = color.equals(ChessPiece.Color.WHITE) ? "White" : "Black";
        System.out.println("User color is: " + colorString);
        String turnString = currentMatch.getTurn().equals(ChessPiece.Color.WHITE) ? "White" : "Black";
        System.out.println("Turn color is: " + turnString);
        this.validMove = color.equals(currentMatch.getTurn()) ? board.move(fromPosition, toPosition, color) : false;
        if (this.validMove == true) {
            BoardWebSocketHandler.notifyUser(new BoardWebSocketHandler.boaredEventTemplate(fromRow, fromColumn, toRow, toColumn), currentMatch.getOpponent(userID));
            currentMatch.nextTurn();
        }
        log.trace("buildResponse -> {}", this);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (IllegalPositionException e) {
            e.printStackTrace();
        }
    }

  /* Methods for Testing */

    public MoveRequest() {
        this.requestType = "move";
    }
}