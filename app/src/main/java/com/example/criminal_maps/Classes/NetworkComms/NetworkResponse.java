package com.example.criminal_maps.Classes.NetworkComms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class NetworkResponse {

    private HashMap<String, Object> response = null;

    public NetworkResponse(String response) throws JSONException {
        this.response = decodeJson(response);

    }

    public void setRespose(String response) throws JSONException {
        this.response = decodeJson(response);

    }


    public boolean isSuccess(){
        if (response.get("response_code").equals("success")){
            return true;
        }
        return false;

    }


    public Object getResponse(String key) {
        return this.response.get(key);

    }

    static public HashMap<String, Object> decodeJson(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        HashMap<String, Object> hashmapJson = new HashMap<>();
        Iterator<String> iter = json.keys();

        while (iter.hasNext()) {
            String key = iter.next();
            Object value = json.get(key);
            // TODO add support for the other instances
            if (value instanceof JSONArray) {
                hashmapJson.put(key,(JSONArray) value);
            }else if (value instanceof String){
                hashmapJson.put(key, (String) value);
            }


        }

        return hashmapJson;
    }

}
