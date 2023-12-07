package websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonReader;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author andrew
 */


@ApplicationScoped
@ServerEndpoint("/actions")
public class UserWebSocketServer {
    @Inject
    private UserSessionHandler sessionHandler;
    
    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) { Logger.getLogger(UserWebSocketServer.class.getName()).log(Level.SEVERE, null, error); }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            // if action is "join", instantiate a new user
            if ("join".equals(jsonMessage.getString("action"))) {
                User user = new User(jsonMessage.getString("username"), jsonMessage.getString("forum"));

                sessionHandler.addSession(session);
                sessionHandler.addSessionToForum(session, user);
            }

            // if action is "update", update the specified forum with the message
            if ("update".equals(jsonMessage.getString("action"))) {
                sessionHandler.updateForum(jsonMessage.getString("forum"), jsonMessage.getString("message"));
            }
        }
    }
}
