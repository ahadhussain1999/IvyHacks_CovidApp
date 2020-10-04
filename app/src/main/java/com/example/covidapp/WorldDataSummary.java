package com.example.covidapp;

import com.google.gson.annotations.SerializedName;

public class WorldDataSummary {
    private int infected;
    private String recovered;
    private String deceased;
    private String country;

    public int getInfected() {
        return infected;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getCountry() {
        return country;
    }
}
