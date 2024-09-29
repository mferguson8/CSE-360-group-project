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
	private int i = 0;
	private GUIController gui;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
    	System.out.println("ASU Hello World!");
    	System.out.println("It started!");
        gui = new GUIController();
        gui.initialize(primaryStage, this);
     
    }
    
    public void onButtonPressed() { //Called by GUIController when someone presses the button
    	i++;
    	gui.setTitle(String.valueOf(i));
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
    
    
    
}
