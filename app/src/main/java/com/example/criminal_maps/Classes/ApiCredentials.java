package com.example.criminal_maps.Classes;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ApiCredentials implements Serializable {
    private String credentials;

    public ApiCredentials(String credentials){
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials){
        this.credentials = credentials;
    }

    @NonNull
    @Override
    public String toString() {
        return "Credentials:" + this.credentials;
    }
}
