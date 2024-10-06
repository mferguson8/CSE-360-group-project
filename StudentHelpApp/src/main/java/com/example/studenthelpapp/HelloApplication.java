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

<<<<<<< HEAD
    public void start(Stage primaryStage) {
<<<<<<< HEAD
        //String test_txt = "test@test.com";
        //System.out.println("test_txt: " + test_txt);
        //Email.EmailResult er = Email.make_email(test_txt);
        //Email.EmailResult.Status ers = er.check();
        //if (ers == Email.EmailResult.Status.SUCCESS) System.out.println(er.get_email().to_string());
        //else System.out.println(Email.EmailResult.parse_status(ers));

=======
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        System.out.println("ASU Hello World!");
        System.out.println("It started!");
>>>>>>> 081d4a2 (fixed maven / java issues)
        primaryStage.setTitle("ASU Hello World Spring 2024");
        Button btn = new Button();
        btn.setText("Display: 'ASU says: Hello World!'");
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.println("ASU: Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
=======
    	System.out.println("ASU Hello World!");
    	System.out.println("It started!");
        gui = new GUIController();
        gui.initialize(primaryStage, this);
     
    }
    
    public void onButtonPressed() { //Called by GUIController when someone presses the button
    	i++;
    	gui.setTitle(String.valueOf(i));
    	System.out.println("Button Pressed!");
<<<<<<< HEAD
    	
>>>>>>> ec8ca11 (GUI Initial and GUIController and Application Interaction)
=======
>>>>>>> f1d25a6 (Added hashString function, which required 2 new imports)
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
