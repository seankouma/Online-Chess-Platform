package com.tco.notifications;//package com.tco.notifications;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.*;
import com.tco.misc.NotificationWebSocketHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;


public class Notifications {

    public static Map<String, Session> userUsernameMap = new ConcurrentHashMap<>();

    public static void notifyUser(String sender, NotificationWebSocketHandler event, String responder, Session session, String notifyTo) {
        System.out.println("In notifyUser function");
        Gson gson = new Gson();
        //session.getRemote().sendString(gson.toJson(event))
        try {
            if (userUsernameMap.containsKey(notifyTo) && userUsernameMap.get(notifyTo).isOpen()) {
                userUsernameMap.get(notifyTo).getRemote().sendString(gson.toJson(event));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}