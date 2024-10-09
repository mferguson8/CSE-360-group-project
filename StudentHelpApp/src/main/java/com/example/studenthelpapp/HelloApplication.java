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

import com.example.studenthelpapp.DatabaseController.RoleCodes;

import java.io.IOException;

public class HelloApplication extends Application {
	private int loggedInUserID;
	private GUIController gui;
	private DatabaseController database;
	private RngStrGen codeGenerator;
    public static void main(String[] args) {
        launch(args);
    }


    public void start(@SuppressWarnings("exports") Stage primaryStage) {
 
        gui = new GUIController();
        gui.initialize(primaryStage, this);

    	database = new DatabaseController();
    	database.connectToDatabase();
        	
    	codeGenerator = new RngStrGen(true, true, true);
    }
    
   
    
    public static String hashString(String input) {
    	try {
	    	MessageDigest digester = MessageDigest.getInstance("SHA-256");
	    	byte[] hashedBytes = digester.digest(input.getBytes());
	    	StringBuilder hexString = new StringBuilder();
	    	
	    	for(byte b: hashedBytes) {
	    		hexString.append(String.format("%02x",b));
	    	} 
	        return hexString.toString();
	        
    	} catch(NoSuchAlgorithmException e) {
        	e.printStackTrace();
        	return null;
        }
    }
    

    public void handleLoginAttempt(String username, String password) {
    	//First check if any users exist
    	if(database.isDatabaseEmpty())
    	{
    		//If the database is empty then they are the first user, and the admin.
    		
    		String passwordSalt = codeGenerator.generate(20);
    		String hashedPassword = hashString(password + passwordSalt);
    		database.createUser(username, hashedPassword, passwordSalt);
    		Integer userID = database.getUserIdByUsername(username);
    		if(userID == null) {
    			System.err.println("Error creating first admin user.");
    			return;
    		}
    		
    		database.addRoleToUser(userID, RoleCodes.ADMIN.get()); 
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
    		String hashedPassword = hashString(password + passwordSalt);
    		boolean loginAttempt = database.loginAttempt(userID, username, hashedPassword);
    		if(!loginAttempt) {
    			gui.showAlert("Wrong Username or Password");
    			System.out.println("Login failed");
    			return;
    		} else if(loginAttempt) {
    			loggedInUserID = userID;
    			
    			//Check if they need to reset their password
    			int passwordResetCheck = database.checkIfPasswordResetRequired(userID);
    			if(passwordResetCheck == 1) {
    				gui.switchScene(gui.changePass());
    				return;
    			} else if(passwordResetCheck == 2) {
    				gui.showAlert("Temporary password has expired. Please contact an admin.");
    				gui.switchScene(gui.login_page());
    				loggedInUserID = -1;
    			}
    			//TODO: Add more error handling here for other values that checkIfPasswordResetRequired can return.
    			
    			
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
    
    public void logoutCurrentUser() {
    	loggedInUserID = -1;
    }
    
    public void directToHomePageOrSelectRole(int userID) {
    	//Only use on users that have been logged in. Check where you are getting the userID from.
    	int[] roles = database.getUsersRoleIds(userID);
		if(roles.length == 0) {
			System.err.println("User ID: " + Integer.toString(userID) +" does not have any roles");
			return;
		} else if( roles.length == 1) {
			if(roles[0] == RoleCodes.ADMIN.get()) {
				gui.switchScene(gui.adminHomePage());
				System.out.println("Directed to Admin Homepage");
			} else if(roles[0] == RoleCodes.INSTRUCTOR.get()) {
				gui.switchScene(gui.instructorHomePage());
				System.out.println("Directed to Instructor Homepage");
			} else if(roles[0] == RoleCodes.STUDENT.get()) {
				gui.switchScene(gui.studentHomePage());
				System.out.println("Directed to Student Homepage");
			}
		} else if(roles.length > 1) {
			gui.switchScene(gui.selectRole(roles));
		}
    }
    
    public void finishUserSetup(String email, String firstName, String middleName, String lastName, String preferredName) {
    	boolean finished = database.registerUser(loggedInUserID, email, firstName, middleName, lastName, preferredName);
    	if(finished) {
    		directToHomePageOrSelectRole(loggedInUserID);
    	}
    }
    
    public void createUser(String invite_code, String username, String password) {
    	//For creating a user when they join using an invite code.
    	//Designed to be called by the create account button handling.
    	
    	//Double checking that invite_code is valid
    	if(!database.checkIfInviteCodeValid(invite_code)) {
    		gui.showAlert("Sorry, that invite code isn't valid anymore. There might have been an error");
    		gui.switchScene(gui.login_page());
    		return;
    	}
    	
    	String password_salt = codeGenerator.generate(20);
    	String hashed_password = hashString(password + password_salt);
    	database.createUser(username, hashed_password, password_salt); //Boolean to test if it works?
    	Integer user_id = database.getUserIdByUsername(username); //A little clunky
    	if(user_id == null) {
    		gui.showAlert("Error creating account. Please talk to an admin.");
    		gui.switchScene(gui.login_page());
    		return;
    	}
    	int[] roles = database.getInviteCodeRoles(invite_code);
    	for(int i: roles) {
    		database.addRoleToUser(user_id, i); //Check for success or failure?
    		database.deleteInviteCodeRole(invite_code, i);
    	}
    	//Now its time to log them out
    	gui.showAlert("Thank you for joining. Please login again using the same information");
		gui.switchScene(gui.login_page());
    }
    
    public void handleInviteCodeAttempt(String invite_code) {
    	if(database.checkIfInviteCodeValid(invite_code)) {
    		gui.switchScene(gui.create_account(invite_code));
    	} else {
    		gui.showAlert("That is not a valid invite code");
    	}
    }
    
    public String createInviteCode(int[] roles) {
    	
    	String inviteCode = HPassword.IC.getIC().get(); 
    	for(int i: roles) {
    		database.addInviteCodeRole(inviteCode, i);
    		//TODO: Check here for problems?
    	}
    	return inviteCode;
    }
    
    
    
    public void adminResetPassword(int user_id) {
    	String newPassword = HPassword.OTP.getOTP().get(); 
    	String password_salt = database.getSaltById(user_id);
    	String newHashedPassword = hashString(newPassword + password_salt);
    	database.adminResetPassword(user_id, newHashedPassword); //Check here for problems? Handle errors from false output.
    	gui.showAlert("The user's new password is: " + newPassword);
    }
    
    public Integer getUsernameId(String username) {
    	return database.getUserIdByUsername(username);
    }
    
    public void resetUserPassword(String newPassword) {
    	if(loggedInUserID == -1) {
    		System.err.print("Trying to reset the password of a user that isn't logged in.");
    		gui.showAlert("An error has occured. Please talk to an admin");
    		gui.switchScene(gui.login_page());
    		return;
    	}
    	String password_salt = database.getSaltById(loggedInUserID);
    	String hashed_password = hashString(newPassword+password_salt);
    	boolean outcome = database.userResetPassword(loggedInUserID, hashed_password);
    	if(outcome) {
    		gui.showAlert("Password reset. Please login again using your new password");
    		gui.switchScene(gui.login_page());
    		loggedInUserID = -1;
    	} else {
    		System.err.print("Error occured during user password resetting.");
    		gui.showAlert("An error has occured. Please talk to an admin");
    		gui.switchScene(gui.login_page());
    		loggedInUserID = -1;
    	}
    }
    
    public void deleteUser(int user_id) {
    	int[] empty = {};
    	database.changeUserRoles(user_id, empty);
    	boolean outcome = database.deleteUser(user_id);
    	if(!outcome) {
    		System.err.println("Failed to delete user with id: " + user_id);
    	}
    }
    
    public void addRoles(int user_id, int[] roles) {
    	boolean outcome;
    	for(int i : roles) {
    		outcome = database.addRoleToUser(user_id, i);
    		if(!outcome) {
    			System.err.println("Error adding role " + i + " to userId: " + user_id);
    		}
    	}
    	//TODO: Deal with potential problems.
    }
    
    public void removeRoles(int user_id, int[] roles) {
    	boolean outcome;
    	for(int i : roles) {
    		outcome = database.deleteRoleFromUser(user_id, i);
    		if(!outcome) {
    			System.err.println("Error deleting role " + i + " from userId: " + user_id);
    		}
    	}
    	//TODO: Deal with potential problems.
    }
    
    public String listUsers() {
    	return database.listUsers();
    }
 
}
