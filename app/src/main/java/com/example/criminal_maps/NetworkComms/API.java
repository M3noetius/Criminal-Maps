package com.example.criminal_maps.NetworkComms;

import com.example.criminal_maps.Classes.Category;
import com.example.criminal_maps.Classes.ApiCredentials;
import com.example.criminal_maps.Classes.Crime;
import com.example.criminal_maps.Classes.NetworkComms.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class API extends Network implements Serializable {
    private String error;

    private ApiCredentials apiCredentials= null;

    public API(){}


    public boolean login(String username, String password) throws JSONException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        httpRequest("/api/login", params, "POST");
        NetworkResponse resp =  new NetworkResponse(this.result.response);

        if (!resp.isSuccess()){
            this.error = (String) resp.getResponse("message");
            return false;
        }

        this.apiCredentials = new ApiCredentials((String) resp.getResponse("message"));

        return true;


    }


    public boolean register(String username, String password) throws JSONException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        httpRequest("/api/register", params, "POST");
        NetworkResponse resp =  new NetworkResponse(this.result.response);

        if (!resp.isSuccess()){
            this.error = (String) resp.getResponse("message");
            return false;
        }

        return true;


    }


    public Crime[] getCrimes() throws JSONException {
        //Do request
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sessionId", this.apiCredentials.getCredentials());

        httpRequest("/api/getCrimes", params, "POST");
        NetworkResponse resp =  new NetworkResponse(this.result.response);

        if (!resp.isSuccess()){
            this.error = (String) resp.getResponse("message");
            return null;
        }

        // Parse data
        JSONArray j_crimes = (JSONArray) resp.getResponse("message");
        Crime[] crimes = new Crime[j_crimes.length()];
        for (int i = 0; i < crimes.length; i++){
            JSONArray j_array = (JSONArray)j_crimes.get(i);
            crimes[i] = new Crime(j_array.getInt(0),
                                j_array.getDouble(1),
                                j_array.getDouble(2),
                                j_array.getString(3),
                                j_array.getString(4),
                                j_array.getInt(5),
                                j_array.getString(6) );
        }

        return crimes;


    }


    public Category[] getCategories() throws JSONException {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sessionId", this.apiCredentials.getCredentials());

        httpRequest("/api/getCategories", params, "POST");
        NetworkResponse resp =  new NetworkResponse(this.result.response);

        if (!resp.isSuccess()){
            this.error = (String) resp.getResponse("message");
            return null;
        }

        JSONArray j_categories = (JSONArray) resp.getResponse("message");
        Category[] categories = new Category[j_categories.length()];
        for (int i = 0; i < j_categories.length(); i++){
            JSONObject j_category = j_categories.getJSONObject(i);
            categories[i] = new Category(j_category.getInt("id"),
                                        j_category.getString("category"));

        }

        return categories;


    }


    public boolean addCrime(Crime crime) throws JSONException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sessionId", this.apiCredentials.getCredentials());
        params.put("longitude", String.valueOf(crime.getLongitude()));
        params.put("latitude", String.valueOf(crime.getLatitude()));
        params.put("title", crime.getName());
        params.put("date", crime.getDate());
        params.put("report", crime.getReport());
        params.put("category_id", String.valueOf(crime.getType()));

        httpRequest("/api/addCrime", params, "POST");
        NetworkResponse resp =  new NetworkResponse(this.result.response);

        if (!resp.isSuccess()){
            this.error = (String) resp.getResponse("message");
            return false;
        }
        return true;


    }


    public String getError() {
        return error;
    }
}
