package com.example.criminal_maps.Classes;

import androidx.annotation.NonNull;

public class ApiCredentials {
    private String credentials;

    public ApiCredentials(String credentials){
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    @NonNull
    @Override
    public String toString() {
        return "Credentials:" + this.credentials;
    }
}
