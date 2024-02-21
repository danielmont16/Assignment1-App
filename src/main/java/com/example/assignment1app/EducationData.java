package com.example.assignment1app;

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
