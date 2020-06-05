/*
 *
 * Class fot defining a crime
 *
 */


package com.example.criminal_maps.Classes;

import androidx.annotation.NonNull;

public class Crime {
    private int id;
    private double longitude;
    private double latitude;
    private String name;
    private String date;
    private int type;
    private String report;

    public Crime(int id, double longitude, double latitude, String name, String date, int type, String report) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.date = date;
        this.type = type;
        this.report = report;
    }

    // Strict use only when adding a new crime
    public Crime(double longitude, double latitude, String name, String date, int type, String report) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.date = date;
        this.type = type;
        this.report = report;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @NonNull
    @Override
    public String toString() {
        String ret = this.id + " | " +
                     this.latitude + " | " +
                     this.longitude + " | " +
                     this.name + " | " +
                     this.date + " | " +
                     this.type +  " | " +
                     this.report;

        return  ret;

    }
}
