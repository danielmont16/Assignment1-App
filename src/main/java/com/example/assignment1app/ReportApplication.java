package com.example.assignment1app;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenue($ million)");


        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistics of Colleges and Universities in Canada");
        lineChart.setPrefHeight(600);

        GridPane gridPane = new GridPane();

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Revenue");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Employment");
        rb2.setToggleGroup(group);

        RadioButton rb3 = new RadioButton("Wages");
        rb3.setToggleGroup(group);

        Button button = new Button("Table View");

        GridPane.setRowIndex(button, 0);
        GridPane.setColumnIndex(button, 3);

        GridPane.setRowIndex(rb1, 0);
        GridPane.setColumnIndex(rb1, 1);

        GridPane.setRowIndex(rb2, 1);
        GridPane.setColumnIndex(rb2, 1);

        GridPane.setRowIndex(rb3, 2);
        GridPane.setColumnIndex(rb3, 1);

        GridPane.setMargin(rb1, new Insets(10, 0, 0, 0));
        GridPane.setMargin(rb2, new Insets(10, 0, 0, 0));
        GridPane.setMargin(rb3, new Insets(10, 0, 10, 0));
        GridPane.setMargin(button, new Insets(0, 0, 0, 100));

        gridPane.getChildren().addAll(rb1, rb2, rb3, button);

        gridPane.setAlignment(Pos.CENTER);
        populateChartRevenue(lineChart);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            lineChart.getData().clear();
            if (newValue == rb1) {
                populateChartRevenue(lineChart);
                yAxis.setLabel("Revenue($ million)");
            } else if (newValue == rb2) {
                populateChartEmployment(lineChart);
                yAxis.setLabel("Employment(Units)");
            } else if (newValue == rb3) {
                populateChartWages(lineChart);
                yAxis.setLabel("Wages($ million)");
            }
        });

        primaryStage.setTitle("Report");
        Image applicationIcon = new Image("file:src/main/resources/com/example/assignment1app/icon.png");
        primaryStage.getIcons().add(applicationIcon);

        VBox vbox = new VBox(lineChart, gridPane);
        Scene scene = new Scene(vbox, 800, 550);
        vbox.getStyleClass().add("vbox");
        scene.getStylesheets().add("file:src/main/java/com/example/assignment1app/styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(e -> secondStage());

    }//end of start method

    private void secondStage() {
        //        creating second stage
        Stage secondStage = new Stage();
        secondStage.setTitle("Table view");

        TableView<EducationData> infoTable = new TableView<>();
        TableColumn<EducationData, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        TableColumn<EducationData, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRevenue()));

        TableColumn<EducationData, String> employmentColumn = new TableColumn<>("employment");
        employmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployment()));

        TableColumn<EducationData, String> wagesColumn = new TableColumn<>("wages");
        wagesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWages()));

        infoTable.getColumns().addAll(yearColumn, revenueColumn, employmentColumn, wagesColumn);

        showData(infoTable);

        VBox vbox1 = new VBox(infoTable);
        Scene scene1 = new Scene(vbox1, 800, 550);
        scene1.getStylesheets().add("file:src/main/java/com/example/assignment1app/styles.css");
        secondStage.setScene(scene1);
        secondStage.show();
    }//end of secondStage method

    private void populateChartRevenue(LineChart<String, Number> lineChart) {

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
    }//end of populateChartRevenue method

    private void populateChartEmployment(LineChart<String, Number> lineChart) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT year, employment FROM educationCanada");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("employment");

            while (resultSet.next()) {
                String year = resultSet.getString("year");
                int employment = resultSet.getInt("employment");
                series.getData().add(new XYChart.Data<>(year, employment));
            }

            lineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of populateChartEmployment method

    private void populateChartWages(LineChart<String, Number> lineChart) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT year, wages FROM educationCanada");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("wages");

            while (resultSet.next()) {
                String year = resultSet.getString("year");
                int wages = resultSet.getInt("wages");
                series.getData().add(new XYChart.Data<>(year, wages));
            }

            lineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of populateChartWages method

    private void showData(TableView<EducationData> infoTable) {
        ObservableList<EducationData> data = FXCollections.observableArrayList();
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM educationCanada");
            while (resultSet.next()) {
                EducationData educationData = new EducationData(resultSet.getString("year"),
                        resultSet.getString("revenue"), resultSet.getString("employment"), resultSet.getString("wages"));
                data.add(educationData);
            }
            infoTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end of showDat method


    public static void main(String[] args) {

        launch();
    }


}//end ReportApplication class
