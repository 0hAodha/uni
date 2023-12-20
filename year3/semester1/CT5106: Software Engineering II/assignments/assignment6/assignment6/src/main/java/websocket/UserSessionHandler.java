/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.spi.JsonProvider;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import model.User;

/**
 *
 * @author andrew
 */

@ApplicationScoped
public class UserSessionHandler {

    // contains the sessions connected
    private final Set<Session> sessions = new HashSet<>();

    // key = session id, value = forum name
    private final HashMap<String, String> sessionForums = new HashMap<>();

    // key = forum name, value = posts on that forum
    private final HashMap<String, ArrayList<String>> forumPosts = new HashMap<>();

    // method to update a forum
    public void updateForum(String forum, String message) {
        // add the forum to forumPosts if it's not already there
        forumPosts.putIfAbsent(forum, new ArrayList<>());

        // add message to forumPosts
        forumPosts.get(forum).add(message);

        // build a json object to send to the connected sessions containing all the update information
        JsonProvider provider = JsonProvider.provider();
        JsonObjectBuilder jsonObj = provider.createObjectBuilder()
                .add("action", "update")
                .add("posts", forumPosts.get(forum).get(forumPosts.get(forum).size() -1));

        // update all connected sessions with the json object
        sendToAllConnectedSessionsByForum(jsonObj.build(), forum);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    // method to add a user session to a particular forum
    public void addSessionToForum(Session session, User user) {
        sessionForums.put(session.getId(), user.getForum());

        JsonObject joinMessage = createJoinMessage(user);
        sendToSession(session, joinMessage);
    }

    public void removeSession(Session session) { sessions.remove(session); }

    private JsonObject createJoinMessage(User user) {
        JsonProvider provider = JsonProvider.provider();

        JsonObjectBuilder joinMessage = provider.createObjectBuilder()
                .add("action", "join")
                .add("username", user.getUsername())
                .add("forum", user.getForum());

        // if the forum is already in forumPosts, add its posts to the joinMessage
        if (forumPosts.containsKey(user.getForum())) {
            JsonArrayBuilder posts = provider.createArrayBuilder(forumPosts.get(user.getForum()));
            joinMessage.add("posts", posts);
        }
        // otherwise, add an empty posts array to the joinMessage
        else {
            JsonArrayBuilder postArr = provider.createArrayBuilder();
            joinMessage.add("posts", postArr);
        }

        return joinMessage.build();
    }

    // method to send data to all the sessions connected to a given forum
    private void sendToAllConnectedSessionsByForum(JsonObject jsonObj, String forum) {
        // loop over each connected session
        for (Session session : sessions) {
            // if the session's id corresponds to the given forum, send the data to the session
            // Check if the session's id is in sessionForums
            if (sessionForums.get(session.getId()).equals(forum)) {
                sendToSession(session, jsonObj);
            }
        }
    }
    
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            System.out.println("session could not be reached");
        }
    }
}