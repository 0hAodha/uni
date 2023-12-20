/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author andrew
 */
public class User {
    private String username;
    private String forum;   // the forum to which the user belongs
    
    public User(String username, String forum) {
	this.username = username;
	this.forum = forum;
    }

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getForum() { return forum; }
    public void setForum(String forum) { this.forum = forum; }
}
