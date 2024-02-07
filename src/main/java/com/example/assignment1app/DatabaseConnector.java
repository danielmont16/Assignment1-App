package com.example.assignment1app;

import java.sql.*;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/db_data";
    private static final String USER = "root";
    private static final String PASS = "mysqlclass";

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }


}//end of databaseConnector


