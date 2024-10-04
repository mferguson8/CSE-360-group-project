package com.example.studenthelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;

public class HelloApplication extends Application {
	private int loggedInUserID;
	private GUIController gui;
	private DatabaseController database;
    public static void main(String[] args) {
        launch(args);
    }


    public void start(@SuppressWarnings("exports") Stage primaryStage) {
    	System.out.println("ASU Hello World!");
    	System.out.println("It started!");
        gui = new GUIController();
        gui.initialize(primaryStage, this);
       
    	database = new DatabaseController();
    	database.connectToDatabase();
        	
     
    }
    
    public void onButtonPressed() { //Called by GUIController when someone presses the button
    	
    	System.out.println("Button Pressed!");
    }
    
    public static String hashString(String input) {
    	try {
	    	MessageDigest digester = MessageDigest.getInstance("SHA-256");
	    	byte[] hashedBytes = digester.digest(input.getBytes());
	    	
	    	String hexString = "";
	    	for(byte b: hashedBytes) {
	    		hexString += String.format("%02x",b);
	    	} 
	        return hexString;
	        
    	} catch(NoSuchAlgorithmException e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    public void HandleLoginAttempt(String username, String password) {
    	//First check if any users exist
    	if(database.isDatabaseEmpty())
    	{
    		//If the database is empty then they are the first user, and the admin.
    		//TODO: Create PasswordSalt
    		String passwordSalt = "";
    		String hashedPassword = hashString(password + passwordSalt);
    		System.out.println("Username: " + username);//TODO: REMOVE, JUST FOR TESTING
    		System.out.println("User password: " + password);//TODO: REMOVE, JUST FOR TESTING
    		System.out.println("User hashed password: " + hashedPassword);//TODO: REMOVE, JUST FOR TESTING
    		database.createUser(username, hashedPassword, passwordSalt);
    		Integer userID = database.getUserIdByUsername(username);
    		if(userID == null) {
    			System.err.println("Error creating and fetching admin account");
    			return;
    		}
    		
    		database.addRoleToUser(userID, 1); //1 is ID for admin
    		gui.showAlert("Welcome Admin. Please login again using the same information");
    		gui.switchScene(gui.login_page());
    	} else {
    		Integer userID = database.getUserIdByUsername(username);
    		if(userID == null) {
    			gui.showAlert("Wrong Username or Password");
    			System.out.println("User doesn't exist");
    			return;
    		}
    		String passwordSalt = database.getSaltById(userID);
    		System.out.println("Username: " + username);//TODO: REMOVE, JUST FOR TESTING
    		System.out.println("User password: " + password);//TODO: REMOVE, JUST FOR TESTING
    		System.out.println("User salt: " + passwordSalt);//TODO: REMOVE, JUST FOR TESTING
    		String hashedPassword = hashString(password + passwordSalt);
    		System.out.println("User hashed password: " + hashedPassword);//TODO: REMOVE, JUST FOR TESTING
    		boolean loginAttempt = database.loginAttempt(userID, username, hashedPassword);
    		if(!loginAttempt) {
    			gui.showAlert("Wrong Username or Password");
    			System.out.println("Login failed");
    			return;
    		} else if(loginAttempt) {
    			loggedInUserID = userID;
    			
    			//Check if they need to reset their password
    			String firstName = database.getFirstNameById(userID); //Checking if registration is done or not?
    			if(firstName == null) {
    				//They need to register
    				gui.switchScene(gui.finishSetUp());
    				return;
    			}
    			
    			directToHomePageOrSelectRole(userID);
    		}
    	}
    }
    
    public void directToHomePageOrSelectRole(int userID) {
    	//Only use on users that have been logged in. Check where you are getting the userID from.
    	int[] roles = database.getUsersRoleIds(userID);
		if(roles.length == 0) {
			System.err.println("User ID: " + Integer.toString(userID) +" does not have any roles");
		} else if( roles.length == 1) {
			if(roles[0] == 1) {
				gui.switchScene(gui.adminHomePage());
				System.out.println("Directed to Admin Homepage");
			} else if(roles[0] == 2) {
				gui.switchScene(gui.instructorHomePage());
				System.out.println("Directed to Instructor Homepage");
			} else if(roles[0] == 3) {
				gui.switchScene(gui.studentHomePage());
				System.out.println("Directed to Student Homepage");
			}
		} else if(roles.length > 1) {
			gui.switchScene(gui.selectRole());
		}
    }
    
    public void finishUserSetup(String email, String firstName, String middleName, String lastName, String preferredName) {
    	boolean finished = database.registerUser(loggedInUserID, email, firstName, middleName, lastName, preferredName);
    	if(finished) {
    		directToHomePageOrSelectRole(loggedInUserID);
    	}
    }
}
