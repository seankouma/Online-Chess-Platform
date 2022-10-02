package com.tco.server;

import com.tco.misc.BadRequestException;
import com.tco.misc.JSONValidator;
import com.tco.requests.*;
import com.tco.misc.NotificationWebSocketHandler;
import com.tco.misc.BoardWebSocketHandler;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import static spark.Spark.*;

import org.eclipse.jetty.websocket.api.annotations.*;

class MicroServer {

    private final Logger log = LoggerFactory.getLogger(MicroServer.class);
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private final int HTTP_OK = 200;
    private final int HTTP_BAD_REQUEST = 400;
    private final int HTTP_SERVER_ERROR = 500;

    MicroServer(int serverPort) {
        configureServer(serverPort);
        processRestfulAPIrequests();
    }

    /* Configure MicroServices Here. */

    private void processRestfulAPIrequests() {
        path("/api", () -> {
            before("/*", (req, res) -> logRequest(req));
            post("/config", (req, res) -> processHttpRequest(req, res, ConfigRequest.class));
            post("/register", (req, res) -> processHttpRequest(req, res, RegisterUserRequest.class));
            post("/login", (req, res) -> processHttpRequest(req, res, LoginRequest.class));
            post("/move", (req, res) -> processHttpRequest(req, res, MoveRequest.class));
            post("/invitations", (req, res) -> processHttpRequest(req, res, InvitationsRequest.class));
            post("/board", (req, res) -> processHttpRequest(req, res, BoardRequest.class));
            post("/creatematch", (req, res) -> processHttpRequest(req, res, CreateMatchRequest.class));
            post("/match", (req, res) -> processHttpRequest(req, res, MatchRequest.class));
        });
    }

    /* You shouldn't need to change what is found below. */

    private String processHttpRequest(spark.Request httpRequest, spark.Response httpResponse, Type requestType) {
        setupResponse(httpResponse);
        String jsonString = httpRequest.body();
        try {
            JSONValidator.validate(jsonString, requestType);
            Request requestObj = new Gson().fromJson(jsonString, requestType);
            return buildJSONResponse(requestObj);
        } catch (IOException | BadRequestException e) {
            log.info("Bad Request - {}", e.getMessage());
            httpResponse.status(HTTP_BAD_REQUEST);
        } catch (Exception e) {
            log.info("Server Error - ", e);
            httpResponse.status(HTTP_SERVER_ERROR);
        }
        return jsonString;
    }

    private void setupResponse(spark.Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        response.status(HTTP_OK);
    }

    private String buildJSONResponse(Request request) throws BadRequestException {
        request.buildResponse();
        String responseBody = new Gson().toJson(request);
        log.info("Response - {}", responseBody);
        return responseBody;
    }

    private void logRequest(spark.Request request) {
        String message = "Request - "
                + "[" + dateTimeFormat.format(LocalDateTime.now()) + "] "
                + request.ip() + " "
                + "\"" + request.requestMethod() + " "
                + request.pathInfo() + " "
                + request.protocol() + "\" "
                + "[" + request.body() + "]";
        log.info(message);
    }

    private void configureServer(int serverPort) {
        port(serverPort);
        String keystoreFile = System.getenv("KEYSTORE_FILE");
        String keystorePassword = System.getenv("KEYSTORE_PASSWORD");
        if (keystoreFile != null && keystorePassword != null) {
            secure(keystoreFile, keystorePassword, null, null);
            log.info("MicroServer running using HTTPS on port {}.", serverPort);
        } else {
            log.info("MicroServer running using HTTP on port {}.", serverPort);
        }

        // To Serve Static Files (SPA)

        staticFiles.location("/public/");
        webSocket("/notifications", NotificationWebSocketHandler.class);
        webSocket("/boardWebsocket", BoardWebSocketHandler.class);
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        before((request, response) -> response.header("Access-Control-Allow-Credentials", "true"));
        redirect.get("/", "/index.html");
    }
}
