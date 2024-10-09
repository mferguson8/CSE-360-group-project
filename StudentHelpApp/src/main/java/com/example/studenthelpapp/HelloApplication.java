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
	private int loggedInUserID; //Store the id of the loggedInUser
	private GUIController gui;	//Object to interface with gui
	private DatabaseController database; //Object to interface with database
	private RngStrGen codeGenerator; //Object to interface with random string generator
	
    public static void main(String[] args) {
    	//main just calls application's launch
        launch(args);
    }


    public void start(@SuppressWarnings("exports") Stage primaryStage) {
    	//Application's launch calls start first
    	
    	//Creates a GUIController object, DatabaseController object, and RngStrGen object
        gui = new GUIController();
        gui.initialize(primaryStage, this);

    	database = new DatabaseController();
    	database.connectToDatabase();
        	
    	codeGenerator = new RngStrGen(true, true, true);
    }
    
   
    
    public static String hashString(String input) {
    	try {
    		//Hashes a given string using SHA-256
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
    	logoutCurrentUser(); //To ensure we don't have any stray userIDs
    	//First check if any users exist
    	if(database.isDatabaseEmpty())
    	{
    		//If the database is empty then they are the first user, and the admin.
    		//Generate them a salt, and add it to their password
    		String passwordSalt = codeGenerator.generate(20);
    		String hashedPassword = hashString(password + passwordSalt);
    		database.createUser(username, hashedPassword, passwordSalt);
    		Integer userID = database.getUserIdByUsername(username);
    		//Generate the user, and fetch the userID
    		if(userID == null) {
    			System.err.println("Error creating first admin user.");
    			return;
    		}
    		//Give them the role of admin
    		database.addRoleToUser(userID, RoleCodes.ADMIN.get()); 
    		gui.showAlert("Welcome Admin. Please login again using the same information");
    		gui.switchScene(gui.login_page());
    	} else {
    		//Get the userID and check if its valid/user exists
    		Integer userID = database.getUserIdByUsername(username);
    		if(userID == null) {
    			gui.showAlert("Wrong Username or Password");
    			System.out.println("User doesn't exist");
    			return;
    		}
    		//Fetch their salt, hash the provided password, and check with the database if it matches
    		String passwordSalt = database.getSaltById(userID);
    		String hashedPassword = hashString(password + passwordSalt);
    		boolean loginAttempt = database.loginAttempt(userID, username, hashedPassword);
    		if(!loginAttempt) {
    			//Log them out, no match found
    			gui.showAlert("Wrong Username or Password");
    			System.out.println("Login failed");
    			return;
    		} else if(loginAttempt) {
    			loggedInUserID = userID; //To keep track of their Id
    			
    			//Check if they need to reset their password
    			int passwordResetCheck = database.checkIfPasswordResetRequired(userID);
    			if(passwordResetCheck == 1) {
    				gui.switchScene(gui.changePass()); //Force them to change their password
    				return;
    			} else if(passwordResetCheck == 2) {
    				//Timeout is up, need an admin to reset again
    				gui.showAlert("Temporary password has expired. Please contact an admin.");
    				gui.switchScene(gui.login_page());
    				loggedInUserID = -1;
    			}
    			//TODO: Add more error handling here for other values that checkIfPasswordResetRequired can return.
    			
    			
    			String firstName = database.getFirstNameById(userID);
    			//Check if their registration is done or not by checking if firstName is null
    			//By default null for users that haven't done registration, and isn't allowed to be empty
    			if(firstName == null) {
    				//They need to register
    				gui.switchScene(gui.finishSetUp());
    				return;
    			}
    			
    			directToHomePageOrSelectRole(userID); //Direct them to the appropriate homepage
    		}
    	}
    }
    
    public void logoutCurrentUser() {
    	//Currently a little jank. -1 is for no user logged in.
    	loggedInUserID = -1;
    }
    
    public void directToHomePageOrSelectRole(int userID) {
    	//Only use on users that have been logged in. Check where you are getting the userID from.
    	int[] roles = database.getUsersRoleIds(userID); //Get their roles
		if(roles.length == 0) {
			//Somehow this user doesn't have roles. Just log them out and error
			System.err.println("User ID: " + Integer.toString(userID) +" does not have any roles");
			gui.switchScene(gui.login_page());
			
			logoutCurrentUser();
			return;
		} else if( roles.length == 1) {
			//If they have 1 role, direct them to that role's homepage
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
			//If they have more than 1 role, direct them to a scene where they can choose which to login as
			gui.switchScene(gui.selectRole(roles));
		}
    }
    
    public void finishUserSetup(String email, String firstName, String middleName, String lastName, String preferredName) {
    	//Passes info from user registration to the database.
    	boolean finished = database.registerUser(loggedInUserID, email, firstName, middleName, lastName, preferredName);
    	if(finished) {
    		//On success, take them to their homepage
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
    	
    	String password_salt = codeGenerator.generate(20); //Generate them a salt
    	String hashed_password = hashString(password + password_salt); 
    	database.createUser(username, hashed_password, password_salt);
    	Integer user_id = database.getUserIdByUsername(username); //Get the id for the new user
    	if(user_id == null) {
    		gui.showAlert("Error creating account. Please talk to an admin.");
    		gui.switchScene(gui.login_page());
    		return;
    	}
    	int[] roles = database.getInviteCodeRoles(invite_code); //Check the roles associated with the invite code
    	for(int i: roles) {
    		//Give them those roles, and delete that role from the invite_code
    		database.addRoleToUser(user_id, i);
    		database.deleteInviteCodeRole(invite_code, i);
    	}
    	//Now its time to log them out
    	gui.showAlert("Thank you for joining. Please login again using the same information");
		gui.switchScene(gui.login_page());
    }
    
    public void handleInviteCodeAttempt(String invite_code) {
    	//Check if its valid. If it is, take them to create_account, else, refuse
    	if(database.checkIfInviteCodeValid(invite_code)) {
    		gui.switchScene(gui.create_account(invite_code));
    	} else {
    		gui.showAlert("That is not a valid invite code");
    	}
    }
    
    public String createInviteCode(int[] roles) {
    	//Generate a new invite code and then add associated roles, then return the code
    	String inviteCode = HPassword.IC.getIC().get(); 
    	for(int i: roles) {
    		database.addInviteCodeRole(inviteCode, i);
    		//TODO: Check here for problems?
    	}
    	return inviteCode;
    }
    
    
    
    public void adminResetPassword(int user_id) {
    	//Generate a new onetime password
    	String newPassword = HPassword.OTP.getOTP().get(); 
    	//Get the user's salt
    	String password_salt = database.getSaltById(user_id);
    	//Salt and hash
    	String newHashedPassword = hashString(newPassword + password_salt);
    	//Then set their password to be the new temp password, as well as their temp pass flag and timeout values
    	database.adminResetPassword(user_id, newHashedPassword); //Check here for problems? Handle errors from false output.
    	gui.showAlert("The user's new password is: " + newPassword); //Provide the admin with the temp password
    }
    
    public Integer getUsernameId(String username) {
    	//fetch userId via username from database
    	return database.getUserIdByUsername(username);
    }
    
    public void resetUserPassword(String newPassword) {
    	//When a user has to make a new password after an admin forced a password reset
    	if(loggedInUserID == -1) {
    		//Make sure they are logged in
    		System.err.print("Trying to reset the password of a user that isn't logged in.");
    		gui.showAlert("An error has occured. Please talk to an admin");
    		gui.switchScene(gui.login_page());
    		return;
    	}
    	//Get their salt and determine the new passwords salted hash
    	String password_salt = database.getSaltById(loggedInUserID);
    	String hashed_password = hashString(newPassword+password_salt);
    	//reset the password and requisite flags
    	boolean outcome = database.userResetPassword(loggedInUserID, hashed_password);
    	if(outcome) {
    		//Display success and log them out
    		gui.showAlert("Password reset. Please login again using your new password");
    		gui.switchScene(gui.login_page());
    		loggedInUserID = -1;
    	} else {
    		//Display failure and log them out
    		System.err.print("Error occured during user password resetting.");
    		gui.showAlert("An error has occured. Please talk to an admin");
    		gui.switchScene(gui.login_page());
    		loggedInUserID = -1;
    	}
    }
    
    public void deleteUser(int user_id) {
    	int[] empty = {};
    	//Remove all the roles from the user (necessary)
    	database.changeUserRoles(user_id, empty);
    	//And then delete them
    	boolean outcome = database.deleteUser(user_id);
    	if(!outcome) {
    		System.err.println("Failed to delete user with id: " + user_id);
    	}
    }
    
    public void addRoles(int user_id, int[] roles) {
    	//Adds roles to the provided user. 
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
    	//Removes roles from the provided user
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
    	//fetches the Users String from the database for the listUsers admin functionality
    	return database.listUsers();
    }
 
}
