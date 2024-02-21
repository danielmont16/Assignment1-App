/**
 * JavaFX's application.
 * Purpose: Graphical user interface that will allow users to retrieve and visualize data from a MySQL database.
 *The application will provide both graphs and charts based on the retrieved data.
 * Author: Daniel Montenegro
 * Date: March 4, 2024
 */

package com.example.assignment1app;

import java.sql.*;
/**
 * The following class creates the connection with the DataBase.
 */
public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/db_data";
    private static final String USER = "root";
    private static final String PASS = "mysqlclass";

    /**
     * The following method establish the connection with the DataBase.
     */
    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }


}//end of databaseConnector


