package com.example.lab10;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;



public class Client extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("hello-view.fxml"));

        //Connect to socket
        Socket s = new Socket();
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(OutputStream.nullOutputStream()));

        try {
            s = new Socket("localhost",63030);
            br = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        }
        catch (IOException e) {
            System.out.println("Could not connect. Please open/restart server and try again");
            Platform.exit();
            System.exit(0);
        }



        //Set Up Client GUI Elements
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 338.75, 217.25);

        //Add Labels & Text Fields
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username:");
        Label messageLabel = new Label("Message");
        TextField messageField = new TextField();
        messageField.setPromptText("Enter Message:");
        //Buttons
        Button sendButton = new Button("Send");
        Button exitButton = new Button("Exit");

        //Buttons
        GridPane grid = new GridPane();
        grid.add(usernameLabel,0,0);
        grid.add(usernameField,1,0);
        grid.add(messageLabel,0,1);
        grid.add(messageField,1,1);
        grid.add(sendButton,0,2);
        grid.add(exitButton,0,3);
        //Format Spacing
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30,30,30,30));
        vbox.getChildren().add(grid);

        //Implement Functionality
        exitButton.setOnAction(actionEvent -> {
            Platform.exit();
            System.exit(0);
        });





//        Send Message
        BufferedWriter finalBr = br;
        sendButton.setOnAction(actionEvent -> {
            try {
                finalBr.write(usernameField.getText() + ": ");
                finalBr.write(messageField.getText());
                messageField.clear();
                finalBr.newLine();
                finalBr.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error Sending Message, Connection Closed");
                Platform.exit();
                System.exit(0);
            }
        });

        BufferedWriter finalBr1 = br;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    try {
                        finalBr1.write(usernameField.getText() + ": ");
                        finalBr1.write(messageField.getText());
                        messageField.clear();
                        finalBr1.newLine();
                        finalBr1.flush();
                    } catch (IOException e) {
                        System.out.println("Error Sending Message, Connection Closed");
                        Platform.exit();
                        System.exit(0);
                    }
                    ke.consume(); // <-- stops passing the event to next node
                }
            }
        });


        //Set Up Client GUI Window
        stage.setTitle("Lab 10 Solution: Simple BBS Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}