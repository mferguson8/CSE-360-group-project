package com.example.studenthelpapp;

import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public abstract class User {
    private Email email;
    private String userName;
    private boolean isOneTimePassword;
    private LocalDateTime oneTimePasswordExpiration;
    private Name name;
    private TopicLevel topicLevel;  // beginner, intermediate, advanced, expert
    private int userID;
    private List<Role> roles;
    private Role activeRole;
    private DatabaseController dbController;
    
    // TO DO:
    // properly integrate with Database.java
    // ids for roles, adjust
    // and update UML
    // hello
    
    public User(String username, String password) {
        
    	dbController = new DatabaseController();
    	dbController.connectToDatabase();
    	
    	boolean success = dbController.createUser(username, password, password_salt); // createUser takes in hashed password
    																// and salt, do i hash it? Andrew communicated to me
    																// to delete the password hash and salt fields
																	// what was the plan on that? I see that HelloApplication creates these 
    																// but i cant access them
    	if (success) {
    		System.out.println("User created");	// for testing, will remove
    		this.userName = username;
    	}
    	
    }

    public void registerUser(int userId, String emailEntered, String firstName, String middleName, String lastName, String preferredName) {
    	
    	boolean success = dbController.registerUser(userId, emailEntered, firstName, middleName, lastName, preferredName);
    	
    	if (success) {
    		System.out.println("User registered");	// for testing, will remove
    		this.userID = userId;
    		this.email = new Email(emailEntered);
    		this.name = new Name(firstName, middleName, lastName, preferredName);
    	}
    }
    
    public enum TopicLevel { 
    	BEGINNER,
    	INTERMEDIATE,
    	EXPERT
    }
    
    public enum Role {	// database uses int for roles, will have to ask what ids for what roles if going that route
    	ADMIN,			
    	INSTRUCTOR,
    	STUDENT
    }
    
    public List<Role> getRoles() {
    	return roles;
    }

    public void setActiveRole(Role role) {
    	if (roles.contains(role)) {
    		this.activeRole = role;
    	}
    	else {
    		throw new IllegalArgumentException("User does not have the selected role");
    	}
    }
    
    public Role getActiveRole() {
    	return activeRole;
    }
    public String getUserName() {
        return userName;
    }

    public TopicLevel getTopicLevel() {
        return topicLevel;
    }

    public void setTopicLevel(TopicLevel topicLevel) {
        this.topicLevel = topicLevel;
    }
    
    private void setUserID(int userID) {
    	this.userID = userID;
    }
    
    private String getUserID() {
    	return this.userID;
    }
    
}
