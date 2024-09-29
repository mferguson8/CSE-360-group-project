//package com.example.studenthelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {
	
	private Stage mainStage;
	private Scene mainScene;
	private HelloApplication helloApp;
	
	public void initialize(Stage mainStage, HelloApplication helloApp) {
		this.mainStage = mainStage;
		this.helloApp = helloApp;
		
		mainStage.setTitle("ASU Hello World Spring 2024");
	        Button btn = new Button();
	        btn.setText("Display: 'ASU says: Hello World!'");
	        btn.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	                System.out.println("ASU: Hello World!");
	                handleButtonPress(); //Calls a private function to handle the button press
	            }
	        });

	        StackPane root = new StackPane();
	        root.getChildren().add(btn);
	        mainScene = new Scene(root, 300, 250);
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
