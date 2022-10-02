package com.tco.misc;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import com.tco.requests.Request;
import com.tco.notifications.Notifications;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@WebSocket
public class BoardWebSocketHandler{

    private String type, sender, msg;
    public static Map<String, Session> boardUserUsernameMap = new ConcurrentHashMap<>();
    public static class boaredEventTemplate{
        int fromRow;
        int fromColumn;
        int toRow;
        int toColumn;
        public boaredEventTemplate(int fromRow, int fromColumn, int toRow, int toColumn){
            this.fromRow = fromRow;
            this.fromColumn = fromColumn;
            this.toRow = toRow;
            this.toColumn = toColumn;
        }
    }
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println(" Successfully established the board connection");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Successfully closed the connection");
        // Notifications.userUsernameMap.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user,String message) {
        Gson gson = new Gson();
        //mapper.readValue(JSON data,Object class.class);
        BoardWebSocketHandler receinedEvent = gson.fromJson(message, BoardWebSocketHandler.class);
        if (receinedEvent.type.equals("Connected")) {
            boardUserUsernameMap.put(receinedEvent.sender, user);
            System.out.println("boardUserUsernameMap" + boardUserUsernameMap);
        }

    }

    public static void notifyUser(boaredEventTemplate event, String notifyTo) {
        System.out.println("In board notifyUser function" + notifyTo);
        Gson gson = new Gson();
        //session.getRemote().sendString(gson.toJson(event))
        try {
            if (boardUserUsernameMap.containsKey(notifyTo) && boardUserUsernameMap.get(notifyTo).isOpen()) {
                boardUserUsernameMap.get(notifyTo).getRemote().sendString(gson.toJson(event));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


}