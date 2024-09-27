//package com.example.studenthelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        //String test_txt = "testtest";
        //System.out.println("test_txt: " + test_txt);
        //Email test = Email.make_email(test_txt);
        //if (test != null) System.out.println(test.to_string());
        //else System.out.println("Got null");

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
    }
}
