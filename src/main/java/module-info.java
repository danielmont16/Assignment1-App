module com.example.assignment1app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.assignment1app to javafx.fxml;
    exports com.example.assignment1app;
}