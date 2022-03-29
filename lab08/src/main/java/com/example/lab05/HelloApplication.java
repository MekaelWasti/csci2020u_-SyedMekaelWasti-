package com.example.lab05;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import java.io.*;

public class HelloApplication extends Application {

    String currentFileName = null;
    String currentFilePath = null;
    DataSource currentSource = new DataSource();

    @Override
    public void start(Stage stage) throws IOException {

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
        tableView.setItems(currentSource.originalMarks());

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
        add.setOnAction(addNewRecord(currentSource,sidInput,assignmentsInput,midtermInput,finalExamInput));

        //Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.getMenus().add(menu);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)","*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        MenuItem menuItem1 = new MenuItem("New");
        MenuItem menuItem2 = new MenuItem("Open");
        MenuItem menuItem3 = new MenuItem("Save");
        MenuItem menuItem4 = new MenuItem("Save As");
        MenuItem menuItem5 = new MenuItem("Exit");

        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);
        menu.getItems().add(menuItem3);
        menu.getItems().add(menuItem4);
        menu.getItems().add(menuItem5);

        menuItem1.setOnAction(e -> {
            tableView.getItems().clear();
            currentFileName = null;
            currentFilePath = null;
        });

        menuItem2.setOnAction(e -> {
            try {
                open(fileChooser, stage,tableView);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        menuItem3.setOnAction(e -> {
            try {
                save(fileChooser,stage,currentSource,currentFilePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        menuItem4.setOnAction(e -> {
            try {
                saveAs(fileChooser,stage,currentSource);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        menuItem5.setOnAction(e -> {
            Platform.exit();});


        //GUI Setup
        VBox vbox = new VBox(menuBar,tableView, grid);
        Scene scene = new Scene(vbox, 1000,500);
        stage.setTitle("Lab 08 Solution");
        stage.setScene(scene);
        stage.show();

        //Save Ctrl+S command
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyCombination = new KeyCodeCombination(KeyCode.S,
                    KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent keyE) {
                if (keyCombination.match(keyE)) {
                    try {
                        save(fileChooser,stage,currentSource,currentFilePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    keyE.consume(); // <-- stops passing the event to next node
                }
            }
        });
        //Save As Ctrl+Shift+S command
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyCombination = new KeyCodeCombination(KeyCode.S,
                    KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
            public void handle(KeyEvent keyE) {
                if (keyCombination.match(keyE)) {
                    try {
                        saveAs(fileChooser,stage,currentSource);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    keyE.consume(); // <-- stops passing the event to next node
                }
            }
        });
    }

    public File open(FileChooser fileChooser, Stage stage, TableView tableView) throws IOException {
        File selectedFile = fileChooser.showOpenDialog(stage);
        load(fileChooser,selectedFile,stage, tableView);
        return selectedFile;
    }

    public void load(FileChooser fileChooser, File selectedFile, Stage stage, TableView tableView) throws IOException {
        fileChooser.setInitialFileName("myCSV.csv");
        currentFilePath = selectedFile.getAbsolutePath();
        currentFileName = selectedFile.getName();
        tableView.getItems().clear();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFilePath));
        String line = null;
        int j = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (j == 0) {
                j++;
                continue;
            }
            String[] column = line.split(",");
            currentSource.addRecord(column[0],Float.parseFloat(column[1]),Float.parseFloat(column[2]),Float.parseFloat(column[3]));
        }
        bufferedReader.close();
        tableView.setItems(currentSource.getAllMarks());
    }

    public void save(FileChooser fileChooser, Stage stage,DataSource source,String filePath) throws IOException {
        if (currentFileName == null) {
            saveAs(fileChooser,stage,source);
            return;
        }

        File file = new File(currentFilePath);
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("SID");
        csvWriter.append(",");
        csvWriter.append("Assignments");
        csvWriter.append(",");
        csvWriter.append("Midterm");
        csvWriter.append(",");
        csvWriter.append("Final Exam");
        csvWriter.append("\n");
        source.marks.forEach((mark) -> {
            try {
                csvWriter.append(mark.getStudentID() + ",");
                csvWriter.append(mark.getAssignments() + ",");
                csvWriter.append(mark.getMidterm() + ",");
                csvWriter.append(mark.getFinalExam() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.flush();
        csvWriter.close();

    }


    public void saveAs(FileChooser fileChooser, Stage stage, DataSource source) throws IOException {
        File selectedFile = fileChooser.showSaveDialog(stage);
        fileChooser.setInitialFileName("myCSV.csv");
        currentFilePath = selectedFile.getAbsolutePath();
        currentFileName = selectedFile.getName();
        save(fileChooser,stage,source,currentFilePath);
    }

    //Handle Adding new records
    public EventHandler<ActionEvent> addNewRecord(DataSource source, TextField sidInput, TextField assignmentsInput, TextField midtermInput, TextField finalExamInput) {
        EventHandler<ActionEvent> onAddClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                source.addRecord(sidInput.getText(), Float.parseFloat(assignmentsInput.getText()),Float.parseFloat(midtermInput.getText()),Float.parseFloat(finalExamInput.getText()));
            }
        };
        return onAddClick;
    }


    public static void main(String[] args) {
        launch();
    }
}