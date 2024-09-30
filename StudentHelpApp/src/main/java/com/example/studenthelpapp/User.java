package com.example.studenthelpapp;

import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public abstract class User {
    private String email;
    private String userName;
    private byte[] passwordHash;  // Non-string data type for password storage
    private boolean isOneTimePassword;
    private LocalDateTime oneTimePasswordExpiration;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredName;
    private String topicLevel;  // beginner, intermediate, advanced, expert
    private String userID;
    private byte[] userSalt;
    
    // TO DO:
    // properly integrate with Database.java
    // and update UML
 

    public User(String email, String userName, byte[] passwordHash, boolean isOneTimePassword, LocalDateTime oneTimePasswordExpiration, 
                String firstName, String middleName, String lastName, String preferredName, String topicLevel) {
        this.email = email;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.isOneTimePassword = isOneTimePassword;
        this.oneTimePasswordExpiration = oneTimePasswordExpiration;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.topicLevel = topicLevel != null ? topicLevel : "intermediate"; // Default is intermediate
    }

    // getters and setters for each field
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPasswordHash(String password) {
        this.passwordHash = hashPasswordWithSalt(password);
    }
    
    private byte[] hashPasswordWithSalt(String password) {
    	// set password hash here with actual text password passed in, then hash it with salt
    	byte[] hashedPassword;
    	try {
    		// Generate salt if not already set
    		if (this.userSalt == null) {
    			setUserSalt();
    		}
    		
    		// Use SHA-256 for hashing the password with the salt
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		
    		// Combine the password bytes with the salt bytes
    		md.update(this.userSalt);
    		hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
    		
    		this.passwordHash = hashedPassword;
    	} catch (NoSuchAlgorithmException e) {
    		throw new RuntimeException("Error occurred during hashing", e);
    	}
    	
    	return hashedPassword;
    }
    
    public boolean authenticate(String enteredPassword) {
    	byte[] hashedEnteredPassword = hashPasswordWithSalt(enteredPassword);
    	return isPasswordCorrect(hashedEnteredPassword);
    }
    
    private boolean isPasswordCorrect(byte[] hashedEnteredPassword) {
    	return Arrays.equals(this.passwordHash, hashedEnteredPassword);
    }

    public boolean isOneTimePassword() {
        return isOneTimePassword;
    }

    public void setOneTimePassword(boolean isOneTimePassword) {
        this.isOneTimePassword = isOneTimePassword;
    }

    public LocalDateTime getOneTimePasswordExpiration() {
        return oneTimePasswordExpiration;
    }

    public void setOneTimePasswordExpiration(LocalDateTime oneTimePasswordExpiration) {
        this.oneTimePasswordExpiration = oneTimePasswordExpiration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getTopicLevel() {
        return topicLevel;
    }

    public void setTopicLevel(String topicLevel) {
        this.topicLevel = topicLevel;
    }

    // Method to check if the one-time password is still valid
    public boolean isOneTimePasswordValid() {
        return isOneTimePassword && LocalDateTime.now().isBefore(oneTimePasswordExpiration);
    }
    
    private void setUserID(String userID) {
    	// have to set actual userID in this method
    	this.userID = userID;
    }
    
    private String getUserID() {
    	return this.userID;
    }
    
    private void setUserSalt() {
    	// have to set actual salt in this method
    	SecureRandom sr = new SecureRandom();
    	byte[] salt = new byte[16];	// 16 bytes for salt
    	sr.nextBytes(salt);
    	
    	this.userSalt = salt;
    }
    
    private byte[] getUserSalt() {
    	return this.userSalt;
    }
    
    // Placeholder for method to reset password
    public void resetPassword(byte[] newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.isOneTimePassword = false; // No longer a one-time password
        this.oneTimePasswordExpiration = null;
    }
}
