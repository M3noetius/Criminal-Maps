package com.example.criminal_map.NetworkComms;

import android.os.Bundle;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Network {
    public ConnectionData result;
    private String sessionId = null;
    private String serverIp = "161.35.35.222";
    private String serverAddress = "http://" + serverIp;
    private int timeout = 5000; //5 seconds

    public Network(){}

    public  Network(String sessionId){
        this.sessionId = sessionId;
    }

    public String login(String username, String password){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        httpRequest("/api/login", params, "POST");

        return this.result.response;
    }

    public String register(String username, String password){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        httpRequest("/api/register", params, "POST");

        return this.result.response;
    }

    private void httpRequest(String file, final HashMap<String, String> params, final String type){
        String TAG = "httpRequest()";
        final String url = serverAddress+file;
        try
        {
            final String myUrl = url;

            Thread mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        result = doHttpUrlConnectionAction(url, params, type);
                    }catch (Exception e){
                        Log.e("ServerComms", e.toString());
                    }
                }
            });
            mThread.start();
            mThread.join();
        }
        catch (Exception e)
        {
            Log.e(TAG,"GOT fUCKEEDDD");
        }

    }

    private ConnectionData doHttpUrlConnectionAction(String url, HashMap<String, String> params, String type)
            throws Exception
    {
        return sendPostRequest(url, params);

    }

    private ConnectionData sendPostRequest(String url, HashMap<String, String> params) throws Exception{
        String TAG = "sendPostRequest()";
        URL _url;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        StringBuilder body_response;

        // Build request body
        String postParameters = buildParams(params);

        // Set up connection
        _url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        DataOutputStream write = new DataOutputStream(connection.getOutputStream());

        // Send data body
        write.writeBytes(postParameters);

        write.flush();
        write.close();


        //connection.getContent(); // is needed for cookie manager to work

        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        body_response = new StringBuilder();

        String line = "";

        while ((line = reader.readLine()) != null)
        {
            body_response.append(line + "\n");
        }

        return  new ConnectionData(connection.getHeaderFields(), body_response.toString());
    }

    private String buildParams(HashMap<String, String> params) throws JSONException {
        JSONObject json2 = new JSONObject(params);

        return json2.toString();
    }

    class ConnectionData {
        public Map<String, List<String>> responseFields;
        public String response;
        public Map<String, String> cookies;

        ConnectionData(Map<String, List<String>> responseFields, String response){
            this.responseFields = responseFields;
            this.response = response;
        }


        @Override
        public String toString() {
            return response + " ############# " + responseFields.toString();
        }
    }
}
