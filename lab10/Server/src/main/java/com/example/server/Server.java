package com.example.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Server extends Application {

    private BufferedWriter bw;
//    public String message = null;
    public String message = "";
    public Socket s;
    public ArrayList<Socket> listOfClients = new ArrayList<Socket>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("hello-view.fxml"));

        //Set Up Server GUI Elements
        VBox vbox = new VBox();

        TextArea messageIncoming = new TextArea();
        Button exitButton = new Button("Exit");

        GridPane grid = new GridPane();
        grid.add(messageIncoming,0,0);
        grid.add(exitButton,0,1);

        grid.setVgap(10);
        grid.setPadding(new Insets(30,30,30,30));
        vbox.getChildren().add(grid);

        //Implement Functionality
        exitButton.setOnAction(actionEvent -> {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });


        //Socket
        ServerSocket ss = new ServerSocket(63030);

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    s = ss.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Socket finalS = s;

                Thread thread1 = new Thread(() -> {
                    while (finalS.isConnected()) {
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new InputStreamReader(finalS.getInputStream()));
                            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                            message = br.readLine();
                            messageIncoming.appendText(message + "\n");

                        } catch (IOException e) {
                            try {
                                finalS.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                thread1.start();
            }
        });
        thread.start();




        //Set Up Client GUI Window
        Scene scene = new Scene(vbox, 338.75, 217.25);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        launch();
    }
}