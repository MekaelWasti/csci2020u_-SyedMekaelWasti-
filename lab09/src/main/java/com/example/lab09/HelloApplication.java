package com.example.lab09;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HelloApplication extends Application {

    //Pane
    Pane pane = new Pane();

    @Override
    public void start(Stage stage) throws IOException {

        //Call Methods
        float[] stockClosingPrices1 = downloadStockPrices("GOOG");
        float[] stockClosingPrices2 = downloadStockPrices("AAPL");
        drawLinePlot(stockClosingPrices1, stockClosingPrices2);

        //Setup GUI Elements
        Scene scene = new Scene(pane, 1280, 720);
        stage.setTitle("Lab 09: Stock Performance");
        stage.setScene(scene);
        stage.show();
    }


    public float[] downloadStockPrices(String tickerSymbol) throws IOException {
        String url = "https://query1.finance.yahoo.com/v7/finance/download/" + tickerSymbol + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
        InputStream in = new URL(url).openStream();
        Files.copy(in, Paths.get("src/main/resources/com/example/lab09/stock.csv"), StandardCopyOption.REPLACE_EXISTING);

        int maxLength = 73;
        float[] stockClosingPrices = new float[maxLength];

        //Get Stock Closing prices and populate array with values
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/lab09/stock.csv"));
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            if (i == 0) {
                i++;
                continue;
            }
            if (i == maxLength) {
                break;
            }
            String[] column = line.split(",");

            //Deal with - out of bounds prices
            if (Float.parseFloat(column[4]) < 0) {
                column[4] = "0";
            }
            else if (Float.parseFloat(column[4]) > 810) {
                column[4] = "810";
            }
            stockClosingPrices[i - 1] = Float.parseFloat(column[4]);
            i++;
        }
        i--;
        stockClosingPrices[stockClosingPrices.length - 1] = i;
        br.close();
        return stockClosingPrices;
    }

    public void drawLinePlot(float[] stockClosingPrices1, float[] stockClosingPrices2) throws IOException {

        //Create Axis Lines
        Line xAxis = new Line(50,50,50,670);
        Line yAxis = new Line(50,670,1230,670);
        pane.getChildren().add(xAxis);
        pane.getChildren().add(yAxis);

        //Draw Stock Charts
        plotLine(stockClosingPrices1,1);
        plotLine(stockClosingPrices2,2);
    }

    public void plotLine(float[] stockClosingPrices, int chart) throws IOException {

        //Plot Lines using Closing Prices From Arrays
        for (int j = 0; j < stockClosingPrices[stockClosingPrices.length-1] - 1; j++) {
            int interval = 15;
            double x = 50.00 + j * interval;
            Line stockLine = new Line(x,670 - stockClosingPrices[j] * 0.75,x + interval,670 - stockClosingPrices[j + 1] * 0.75);
            if (chart == 1) {
                stockLine.setStroke(Color.RED);
            }
            else {
                stockLine.setStroke(Color.BLUE);
            }
            pane.getChildren().add(stockLine);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}