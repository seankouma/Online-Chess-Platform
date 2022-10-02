package com.tco.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.*;
import java.sql.*;
import com.tco.misc.SQLDatabase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.tco.misc.NotificationWebSocketHandler;

public class InvitationsRequest extends Request {

    ArrayList<NotificationWebSocketHandler> invitations = new ArrayList<NotificationWebSocketHandler>();
    ArrayList<JSONObject> users;
    String username;

    private final transient Logger log = LoggerFactory.getLogger(InvitationsRequest.class);

    @Override
    public void buildResponse() {
        try {
            this.invitations = NotificationWebSocketHandler.getNotificationsForUser(username);
            SQLDatabase DB = new SQLDatabase();
            this.users = DB.getAllUsers();
//            JSONArray array = new JSONArray();
//            ArrayList<JSONObject> users = new ArrayList<JSONObject>();
//            JSONObject record = new JSONObject();
//            record.put("sender", "user1");
//            record.put("responder", "user2");
//            record.put("type", "Accept");
//            record.put("msg", "messahe");
//            this.invitations.add(record);
//            JSONObject record2 = new JSONObject();
//            record2.put("sender", "user2");
//            record2.put("responder", "user3");
//            record2.put("type", "Reject");
//            record2.put("msg", "messahe");
//            this.invitations.add(record2);
//            JSONObject record3 = new JSONObject();
//            record3.put("sender", "user3");
//            record3.put("LastName", "user4");
//            record3.put("responder", "Accept");
//            record3.put("type", "messahe");
//            this.invitations.add(record3);
//            JSONObject record4 = new JSONObject();
//            record4.put("sender", "user4");
//            record4.put("responder", "user5");
//            record4.put("type", "Accept");
//            record4.put("msg", "messahe");
//            this.invitations.add(record4);
//            JSONObject record5 = new JSONObject();
//            record5.put("sender", "user5");
//            record5.put("responder", "user6");
//            record5.put("type", "Reject");
//            record5.put("msg", "messahe");
//            this.invitations.add(record5);
//
//            JSONObject record6 = new JSONObject();
//            record6.put("sender", "user1");
//            record6.put("responder", "user2");
//            record6.put("type", "Invite");
//            record6.put("msg", "messahe");
//            this.invitations.add(record6);
//            JSONObject record7 = new JSONObject();
//            record7.put("sender", "user2");
//            record7.put("responder", "user3");
//            record7.put("type", "Invite");
//            record7.put("msg", "messahe");
//            this.invitations.add(record7);
//            JSONObject record8 = new JSONObject();
//            record8.put("sender", "user3");
//            record8.put("responder", "user4");
//            record8.put("type", "Invite");
//            record8.put("msg", "messahe");
//            this.invitations.add(record8);

            log.trace("buildResponse -> {}", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Methods for Testing */

    public InvitationsRequest() {
        this.requestType = "invitations";
    }
}