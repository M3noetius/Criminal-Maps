package com.example.criminal_maps.Classes.NetworkComms;


import java.util.List;
import java.util.Map;

public class ConnectionData {
    public Map<String, List<String>> responseFields;
    public String response;
    public Map<String, String> cookies;

    public ConnectionData(Map<String, List<String>> responseFields, String response) {
        this.responseFields = responseFields;
        this.response = response;
    }


    @Override
    public String toString() {
        return response + " ############# " + responseFields.toString();
    }
}