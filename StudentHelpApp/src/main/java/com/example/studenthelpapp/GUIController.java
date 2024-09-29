package com.example.studenthelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

	public void initialize(Stage mainStage, HelloApplication helloApp) {
		this.mainStage = mainStage;
		this.helloApp = helloApp;
		
		mainStage.setTitle("ASU Hello World Spring 2024");
		
		
			TextField enterUsername = new TextField(); 
			enterUsername.setPromptText("Enter username to log-in: ");
			
			
	        Button login = new Button();
	        login.setText("Login");
			Button register = new Button();
			register.setText("Register"); 
	        login.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	                System.out.println("You pressed log in!");
	                String username = enterUsername.getText();
	                System.out.println("Username: " + username);
	                enterUsername.clear();
	                handleButtonPress(); //Calls a private function to handle the button press
	            }
	        });
	        
			register.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	                System.out.println("You pressed sign up!");
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
	
}
