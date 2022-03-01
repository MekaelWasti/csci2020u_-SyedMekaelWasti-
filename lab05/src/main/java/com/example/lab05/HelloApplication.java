package com.example.lab05;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.controlsfx.control.action.Action;


import java.io.IOException;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DataSource source = new DataSource();
        TableView tableView = new TableView();

        TableColumn<StudentRecord, String> SID = new TableColumn<>("StudentID");
        TableColumn<StudentRecord, String> Assignments = new TableColumn<>("Assignments");
        TableColumn<StudentRecord, String> Midterm = new TableColumn<>("Midterm");
        TableColumn<StudentRecord, String> FinalExam = new TableColumn<>("Final Exam");
        TableColumn<StudentRecord, String> FinalMark = new TableColumn<>("Final Mark");
        TableColumn<StudentRecord, String> LetterGrade = new TableColumn<>("Letter Grade");

        SID.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("StudentID"));
        SID.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        Assignments.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("Assignments"));
        Assignments.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        Midterm.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("Midterm"));
        Midterm.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        FinalExam.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("FinalExam"));
        FinalExam.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        FinalMark.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("FinalMark"));
        FinalMark.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        LetterGrade.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("LetterGrade"));
        LetterGrade.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));

        tableView.getColumns().add(SID);
        tableView.getColumns().add(Assignments);
        tableView.getColumns().add(Midterm);
        tableView.getColumns().add(FinalExam);
        tableView.getColumns().add(FinalMark);
        tableView.getColumns().add(LetterGrade);
        tableView.setItems(source.getAllMarks());

        //Extra Challenge Section

        Label sidLabel = new Label("SID:");
        TextField sidInput = new TextField();
        sidInput.setPromptText("SID");
        sidLabel.setPadding(new Insets(10));

        Label assignmentsLabel = new Label("Assignments:");
        TextField assignmentsInput = new TextField();
        assignmentsInput.setPromptText("Assignments/100");
        assignmentsLabel.setPadding(new Insets(10));

        Label midtermLabel = new Label("Midterm:");
        TextField midtermInput = new TextField();
        midtermInput.setPromptText("Midterm/100");
        midtermLabel.setPadding(new Insets(10));

        Label finalExamLabel = new Label("Final Exam:");
        TextField finalExamInput = new TextField();
        finalExamInput.setPromptText("Final Exam/100");
        finalExamLabel.setPadding (new Insets(10));

        Button add = new Button("Add");

        //Grid Setup
        GridPane grid = new GridPane();
        grid.add(sidLabel, 0,0);
        grid.add(sidInput, 1,0);

        grid.add(assignmentsLabel, 2,0);
        grid.add(assignmentsInput, 3,0);

        grid.add(midtermLabel, 0,1);
        grid.add(midtermInput, 1,1);

        grid.add(finalExamLabel, 2,1);
        grid.add(finalExamInput, 3,1);

        grid.add(add,1,2);
        grid.setPadding(new Insets(10,0,20,0));

        //Handle Adding new records
        EventHandler<ActionEvent> onAddClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                source.addRecord(sidInput.getText(), Float.parseFloat(assignmentsInput.getText()),Float.parseFloat(midtermInput.getText()),Float.parseFloat(finalExamInput.getText()));
            }
        };

        add.setOnAction(onAddClick);

        //GUI Setup
        VBox vbox = new VBox(tableView, grid);
        Scene scene = new Scene(vbox, 1000,500);
        stage.setTitle("Lab 05 Solution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}