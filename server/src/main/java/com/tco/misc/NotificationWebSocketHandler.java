package com.tco.misc;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import com.tco.requests.Request;
import com.tco.notifications.Notifications;
import com.google.gson.Gson;
import java.util.ArrayList;

@WebSocket
public class NotificationWebSocketHandler{

    private String type, sender, responder, msg;

    public static class notificationTemplate {
        String type;
        String sender;
        String responder;
        String msg;
    }
    public static ArrayList<NotificationWebSocketHandler> userNotifications = new ArrayList<NotificationWebSocketHandler>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
       System.out.println(" Successfully established the connection");
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
        NotificationWebSocketHandler receinedEvent = gson.fromJson(message, NotificationWebSocketHandler.class);
        if (receinedEvent.type.equals("Accept") || receinedEvent.type.equals("Reject")  || receinedEvent.type.equals("Invite")) {
            // update if record exist otherwise insert record into notification table
            if (userNotifications.size() > 0){
                for (int i = 0; i < userNotifications.size() ; i++) {
                    if (userNotifications.get(i).sender.equals(receinedEvent.sender) && userNotifications.get(i).responder.equals(receinedEvent.responder)) {
                        userNotifications.get(i).sender = receinedEvent.sender;
                        userNotifications.get(i).responder = receinedEvent.responder;
                        userNotifications.get(i).type = receinedEvent.type;
                        userNotifications.get(i).msg = receinedEvent.msg;
                    } else {
                        userNotifications.add(receinedEvent);
                    }
                }
            } else {
                userNotifications.add(receinedEvent);
            }
            System.out.println("userNotifications" + userNotifications.get(0).sender);
            if (receinedEvent.type.equals("Invite")) {
                Notifications.notifyUser(receinedEvent.sender, receinedEvent, receinedEvent.responder, user, receinedEvent.responder);
            } else {
                Notifications.notifyUser(receinedEvent.sender, receinedEvent, receinedEvent.responder, user, receinedEvent.sender);
            }

        } else if (receinedEvent.type.equals("Connected")) {
            Notifications.userUsernameMap.put(receinedEvent.sender, user);
            System.out.println("userUsernameMap" + Notifications.userUsernameMap);
        } else if (receinedEvent.type.equals("Start Match")) {
            if (userNotifications.size() > 0){
                for (int i = 0; i < userNotifications.size() ; i++) {
                    if (userNotifications.get(i).sender.equals(receinedEvent.sender) && userNotifications.get(i).responder.equals(receinedEvent.responder)) {
                        userNotifications.remove(i);
                    }
                }
            }
        }

    }

    public static ArrayList<NotificationWebSocketHandler> getNotificationsForUser(String user){
        System.out.println("user" + user);
        System.out.println("userNotifications" + userNotifications);
        ArrayList<NotificationWebSocketHandler> userSpecificNotifications = new ArrayList<NotificationWebSocketHandler>();
        for (int i = 0; i < userNotifications.size(); i++) {
            if (userNotifications.get(i).sender.equals(user) || userNotifications.get(i).responder.equals(user)) {
                userSpecificNotifications.add(userNotifications.get(i));
            }
        }
        System.out.println("userSpecificNotifications :" + userSpecificNotifications);
        return userSpecificNotifications;
    }


}