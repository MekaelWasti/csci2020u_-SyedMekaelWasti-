package com.example.lab06;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.stream.IntStream;

public class HelloApplication extends Application {

    //Bar Chart Data
    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    //Pie Chart Data
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static String[] pieColours = {
            "PLUM", "LAWNGREEN", "DARKSALMON", "DARKORANGE",
            "GOLD", "AQUA"
    };

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        //Bar Chart START
        //Bar Chart START

        //Configuring category and NumberAxis
        CategoryAxis xAxisB= new CategoryAxis();
        NumberAxis yAxisB = new NumberAxis();
        xAxisB.setLabel("Year");
        yAxisB.setLabel("Avg Pricing");

        //Configuring BarChart
        BarChart bar = new BarChart(xAxisB,yAxisB);
        bar.setTitle("Avg Pricing By Year");

        //Configuring Series for XY chart
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Avg Housing Pricing");
        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Avg Commercial Pricing");

        for (int i = 0; i < avgHousingPricesByYear.length; i++) {
            series1.getData().add(new XYChart.Data<>("" + i,avgHousingPricesByYear[i]));
        }
        for (int i = 0; i < avgCommercialPricesByYear.length; i++) {
            series2.getData().add(new XYChart.Data<>("" + i,avgCommercialPricesByYear[i]));
        }

        //Adding series to the barchart
        bar.getData().add(series1);
        bar.getData().add(series2);

        //Bar Chart END
        //Bar Chart END

        //Pie Chart START
        //Pie Chart START

        double sum = IntStream.of(purchasesByAgeGroup).sum();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = ageGroups.length - 1; i >= 0; i--) {
            pieChartData.add(new PieChart.Data(ageGroups[i] + "  |  " + String.format("%.2f", purchasesByAgeGroup[i]/sum * 100) + "%",purchasesByAgeGroup[i]));
        }

        PieChart pie = new PieChart(pieChartData);
        pie.setTitle("Purchase By Age Group");

        //Pie Chart END
        //Pie Chart END

        // configuring group and scene
        HBox hBox = new HBox();
        hBox.getChildren().add(bar);
        hBox.getChildren().add(pie);
        Scene scene = new Scene(hBox, 1000, 500);
        stage.setTitle("Lab 06 Solution!");
        stage.setScene(scene);
        stage.show();

        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + pieColours[i % pieColours.length] + ";");
            i++;
        }
    }

        public static void main(String[] args) {
            launch();
    }
}