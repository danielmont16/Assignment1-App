/**
 * JavaFX's application.
 * Purpose: Graphical user interface that will allow users to retrieve and visualize data from a MySQL database.
 *The application will provide both graphs and charts based on the retrieved data.
 * Author: Daniel Montenegro
 * Date: March 4, 2024
 */

package com.example.assignment1app;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class ReportApplication extends Application {
    Scene scene, scene1;

    @Override
    public void start(Stage primaryStage) {

        //Creating first scene (graph-view)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenue ($ million)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

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
        button.getStyleClass().add("button");

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
                yAxis.setLabel("Revenue ($ million)");
            } else if (newValue == rb2) {
                populateChartEmployment(lineChart);
                yAxis.setLabel("Employment (Units)");
            } else if (newValue == rb3) {
                populateChartWages(lineChart);
                yAxis.setLabel("Wages ($ million)");
            }
        });

        primaryStage.setTitle("Report");
        Image applicationIcon = new Image("file:src/main/resources/com/example/assignment1app/icon.png");
        primaryStage.getIcons().add(applicationIcon);

        Label reportTitle = new Label("Statistics of Colleges and Universities in Canada");
        reportTitle.setPrefWidth(800);
        reportTitle.getStyleClass().add("reportTitle");

        Label source = new Label("Source: Guirguis, J. (2024, February 2). Colleges & Universities in Canada. Ibisworld;https://my.ibisworld.com/ca/en/industry/61131aca/at-a-glance");
        source.setPrefWidth(800);
        source.getStyleClass().add("source");

        VBox vbox = new VBox(reportTitle, lineChart, gridPane, source);
        VBox.setMargin(reportTitle, new Insets(15, 0, 15, 0));
        VBox.setMargin(source, new Insets(25, 0, 0, 0));

        scene = new Scene(vbox, 800, 550);

        vbox.getStyleClass().add("vbox");
        //using a .css file to style the scene
        scene.getStylesheets().add("file:src/main/java/com/example/assignment1app/styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(e -> primaryStage.setScene(scene1));

        //Creating second scene (table-view)

        TableView<EducationData> infoTable = new TableView<>();
        infoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<EducationData, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        TableColumn<EducationData, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRevenue()));

        TableColumn<EducationData, String> employmentColumn = new TableColumn<>("employment");
        employmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployment()));

        TableColumn<EducationData, String> wagesColumn = new TableColumn<>("wages");
        wagesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWages()));

        List<TableColumn<EducationData, String>> columnList = Arrays.asList(yearColumn, revenueColumn, employmentColumn, wagesColumn);
        infoTable.getColumns().addAll(columnList);

        showData(infoTable);

        Label tableViewTitle = new Label("Statistics");
        tableViewTitle.getStyleClass().add("tableViewTitle");

        GridPane containerTable = new GridPane();
        GridPane.setRowIndex(infoTable, 0);
        GridPane.setColumnIndex(infoTable, 1);

        containerTable.getChildren().add(infoTable);
        containerTable.setPrefWidth(800);
        containerTable.setAlignment(Pos.CENTER);

        Label source1 = new Label("Source: Guirguis, J. (2024, February 2). Colleges & Universities in Canada. Ibisworld;https://my.ibisworld.com/ca/en/industry/61131aca/at-a-glance");
        source1.setPrefWidth(800);
        source1.getStyleClass().add("source");

        Button button1 = new Button("Graph View");

        VBox vbox1 = new VBox(tableViewTitle, containerTable, button1, source1);

        VBox.setMargin(tableViewTitle, new Insets(25, 0, 10, 0));
        VBox.setMargin(containerTable, new Insets(25, 25, 10, 25));
        VBox.setMargin(source1, new Insets(25, 0, 0, 0));
        vbox1.setAlignment(Pos.TOP_CENTER);


        scene1 = new Scene(vbox1, 800, 550);
        vbox1.getStyleClass().add("vbox1");
        //using a .css file to style the scene
        scene1.getStylesheets().add("file:src/main/java/com/example/assignment1app/styles.css");

        //using one button to switch between scenes
        button1.setOnAction(e -> primaryStage.setScene(scene));

    }//end of start method

    /**
     * The following method populates the graph with the Revenue data from DataBase.
     */
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
        finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }//end of populateChartRevenue method

    /**
     * The following method populates the graph with the Employment data from DataBase.
     */
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

        finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//end of populateChartEmployment method

    /**
     * The following method populates the graph with the Wages data from DataBase.
     */
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

        finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//end of populateChartWages method

    /**
     * The following method populates the Table with all data from DataBase.
     */
    private void showData(TableView<EducationData> infoTable) {
        ObservableList<EducationData> data = FXCollections.observableArrayList();
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();
        try {
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
        finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//end of showData method


    public static void main(String[] args) {

        launch();
    }


}//end ReportApplication class
