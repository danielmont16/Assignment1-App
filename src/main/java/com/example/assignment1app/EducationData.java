/**
 * JavaFX's application.
 * Purpose: Graphical user interface that will allow users to retrieve and visualize data from a MySQL database.
 *The application will provide both graphs and charts based on the retrieved data.
 * Author: Daniel Montenegro
 * Date: March 4, 2024
 */

package com.example.assignment1app;
/**
 * The following class establish the model of the tableview.
 */
public class EducationData {
    private final String year;
    private final String revenue;
    private final String employment;
    private final String wages;

    public EducationData(String year, String revenue, String employment, String wages) {
        this.year = year;
        this.revenue = revenue;
        this.employment = employment;
        this.wages = wages;
    }

    public String getYear() {
        return year;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getEmployment() {
        return employment;
    }

    public String getWages() {
        return wages;
    }

}
