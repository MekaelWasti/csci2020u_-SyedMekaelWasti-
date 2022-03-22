package com.example.lab07;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class HelloApplication extends Application {

    //Pie Chart Data
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    @Override
    public void start(Stage stage) throws IOException {

        //Setup map to record weather warning counts
        Map<String, Integer> record = new HashMap<String, Integer>();
        Iterator<Integer> iterator = record.values().iterator();
        record.put("Flash Flood", 0);
        record.put("Severe Thunderstorm", 0);
        record.put("Special Marine", 0);
        record.put("Tornado", 0);
        double count = 0;


        //Read data
        BufferedReader bufferReader = new BufferedReader(new FileReader("weatherwarnings-2015.csv"));
        String line;
        while ((line = bufferReader.readLine()) != null) {

            //Keep count of how many records
            count++;

            // use comma as separator
            String[] column = line.split(",");
            if (column[5].equals("FLASH FLOOD")) {
                record.replace("Flash Flood",(int)(record.get("Flash Flood")) + 1);
            }
            else if (column[5].equals("SEVERE THUNDERSTORM")) {
                record.replace("Severe Thunderstorm",(int)(record.get("Severe Thunderstorm")) + 1);
            }
            else if (column[5].equals("SPECIAL MARINE")) {
                record.replace("Special Marine",(int)(record.get("Special Marine")) + 1);
            }
            else if (column[5].equals("TORNADO")) {
                record.replace("Tornado",(int)(record.get("Tornado")) + 1);
            }
        }
        bufferReader.close();


//        //Pie Chart START
//        //Pie Chart START
//
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : record.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + "  |  " + String.format("%.2f",entry.getValue()/count * 100) + "%",entry.getValue()));
        }

        PieChart pie = new PieChart(pieChartData);
        pie.setTitle("Weather Warnings Count");


//        //Pie Chart END
//        //Pie Chart END

        pie.setLegendSide(Side.LEFT);

        StackPane pane = new StackPane();
        pane.getChildren().add(pie);
        Scene scene = new Scene(pane, 1000, 500);
        stage.setTitle("Lab 07 Solution");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}