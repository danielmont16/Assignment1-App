package com.example.assignment1app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Report");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("revenue");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Colleges and Universities in Canada Revenue");

        populateChart(lineChart);

        Scene scene = new Scene(lineChart, 800 , 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populateChart(LineChart<String, Number> lineChart) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT year, revenue FROM educationCanada");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("revenue");

            while (resultSet.next()) {
                String year = resultSet.getString("year");
                int revenue = resultSet.getInt("revenue");
                series.getData().add(new XYChart.Data<>(year, revenue));
            }

            lineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        launch();
    }




}//end ReportApplication class