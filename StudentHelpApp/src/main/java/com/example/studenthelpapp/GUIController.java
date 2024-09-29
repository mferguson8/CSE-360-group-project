package com.example.studenthelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class GUIController extends HelloApplication {
	
	private Stage mainStage;
	private Scene mainScene;
	private HelloApplication helloApp;
	
    public static void main(String[] args) {
        launch(args);
    }

	/* ***** NOTE: Since the expected layout is a little confusing on the Phase 1 requirements, 
	So far I have mainly worked on making the scenes. Later, once I have the scenes set up,
	I will then focus on connecting them and figuring out where they need to be 
	(for example, which scene pops up when invite code is entered vs when username is entered,
	and what pops up depending on if they need to finish set up, or if they are already fully
	set up). Also obviously the scenes will eventually look neater. 
	*/

	public void initialize(Stage mainStage, HelloApplication helloApp) {
		this.mainStage = mainStage;
		this.helloApp = helloApp;
		
		mainStage.setTitle("Login or Register");
		
		
			TextField enterUsername = new TextField(); 
			enterUsername.setPromptText("Enter username to log in: ");
			
			TextField enterCode = new TextField(); 
			enterCode.setPromptText("Enter a code to set up: ");
			
			
	        Button login = new Button();
	        login.setText("Login");
			Button register = new Button();
			register.setText("Register"); 

			// TESTING, Stuff here will change
	        login.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	                System.out.println("You pressed log in!");
	                String username = enterUsername.getText();
	                System.out.println("Username: " + username);
					// Need to test that username exists
					// If not, send to failure scene or don't change and pop-up message that it 
					// was not found

	                enterUsername.clear();
	                
	                finishSetUpScene(); // This is here for now to test if the scene change worked,
										// and so that I can see what the scene looked like
	                handleButtonPress(); //Calls a private function to handle the button press
	            }
	        });
	        
			// TESTING, stuff here will change
			register.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	                System.out.println("You pressed sign up!");
	                String code = enterCode.getText();
	                // Not sure if code is only numbers, just put String for now
					// Check if one-time invite code is valid
					// If not, fails, if so, change scene
	                
	                System.out.println("Code: " + code);
	                enterCode.clear();
	                
	                registrationScene();// This is here for now to test if the scene change worked,
										// and so that I can see what the scene looked like
	                handleButtonPress(); //Calls a private function to handle the button press
	            }
	        });

	        Pane root = new Pane();

			login.setLayoutX(100);
			login.setLayoutY(250);
			root.getChildren().add(login);

			register.setLayoutX(300);
			register.setLayoutY(250);
			root.getChildren().add(register);
			
			enterUsername.setLayoutX(100);
			enterUsername.setLayoutY(300);
			root.getChildren().add(enterUsername);
			
			enterCode.setLayoutX(300);
			enterCode.setLayoutY(300);
			root.getChildren().add(enterCode);
			
			mainScene = new Scene(root, 500, 500);

			mainStage.setScene(mainScene);
			mainStage.show();

	}
	
	
	public void setTitle(String title) {
		mainStage.setTitle(title);
	}
	
	private void handleButtonPress() { //Triggered when someone presses the button
		if(helloApp != null) {
			helloApp.onButtonPressed(); //Calls a function in HelloApplication
		}
	}
	
	// Work in progress
	private void registrationScene() {
		// Buttons and Textboxes
		
		Button setUsername = new Button();
		setUsername.setText("Create Username");
		
		// Layout
		
		Pane registrationRoot = new Pane();
		setUsername.setLayoutX(100);
		setUsername.setLayoutY(250);
		registrationRoot.getChildren().add(setUsername);
		
		// Set scene
		Scene registrationScene = new Scene(registrationRoot, 500, 500);
		mainStage.setScene(registrationScene);
		
		
	}
	
	// Work in progress
	private void finishSetUpScene() {
		// Buttons and Textboxes
		
		TextField enterEmail = new TextField(); 
		enterEmail.setPromptText("Enter email: ");
		
		TextField enterFirstName = new TextField(); 
		enterFirstName.setPromptText("Enter first name: ");
		
		TextField enterMiddleName = new TextField(); 
		enterMiddleName.setPromptText("Enter middle name: ");
		
		TextField enterLastName = new TextField(); 
		enterLastName.setPromptText("Enter last name: ");
		
		TextField enterPreferredName = new TextField(); 
		enterPreferredName.setPromptText("Enter preferred name: ");
		
		Button setEmail = new Button();
		setEmail.setText("Enter Email");
		
		Button firstName = new Button();
		firstName.setText("Enter first name");
		
		Button middleName = new Button();
		middleName.setText("Enter middle name");
		
		Button lastName = new Button();
		lastName.setText("Enter last name");
		
		Button preferredName = new Button();
		preferredName.setText("Enter preferred name");
		
		// Layout
		
		VBox finishSetUpRoot = new VBox(20);
		
		finishSetUpRoot.getChildren().add(enterEmail);
		finishSetUpRoot.getChildren().add(setEmail);
		finishSetUpRoot.getChildren().add(enterFirstName);
		finishSetUpRoot.getChildren().add(firstName);
		finishSetUpRoot.getChildren().add(enterMiddleName);
		finishSetUpRoot.getChildren().add(middleName);
		finishSetUpRoot.getChildren().add(enterLastName);
		finishSetUpRoot.getChildren().add(lastName);
		finishSetUpRoot.getChildren().add(enterPreferredName);
		finishSetUpRoot.getChildren().add(preferredName);
		
		// Set scene
		Scene finishSetUpScene = new Scene(finishSetUpRoot, 500, 500);
		mainStage.setScene(finishSetUpScene);
		
	}
	
}
