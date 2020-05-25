package com.example.criminal_maps;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.criminal_maps.Classes.ApiCredentials;
import com.example.criminal_maps.Classes.Category;
import com.example.criminal_maps.Classes.Crime;
import com.example.criminal_maps.Classes.NetworkComms.NetworkResponse;
import com.example.criminal_maps.Classes.StringSerialize;
import com.example.criminal_maps.NetworkComms.API;

import java.io.IOException;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void check_json_decode() throws JSONException {
        HashMap<String, Object> jsonHashmap = NetworkResponse.decodeJson("{'name' : 'admin'}");
        for (String key : jsonHashmap.keySet()) {
            System.out.println(key + " : " + jsonHashmap.get(key));
        }


    }


    @Test
    public void check_api_login() throws JSONException {
        API net = new API();
        Boolean response = net.login("admin", "1");
        assertEquals(true, response);


    }


    @Test
    public void check_api_register() throws JSONException {
        API net = new API();
        boolean resp = net.register("admin", "1");
        if (!resp){
            System.out.println("[!] " + net.getError());
        }


    }


    @Test
    public void check_api_getCrimes() throws JSONException {
        API api = new API();
        Boolean response = api.login("admin", "1");
        assertEquals(true, response);

        Crime[] crimes = api.getCrimes();
        for (int i = 0; i < crimes.length; i++){
            System.out.println(crimes[i]);
        }


    }


    @Test
    public void check_api_getCategories() throws JSONException {
        API api = new API();
        Boolean response = api.login("admin", "1");
        assertEquals(true, response);

        Category[] categories = api.getCategories();
        for (int i = 0; i < categories.length; i++){
            System.out.println(categories[i]);
        }


    }

    @Test
    public void check_api_addCrime() throws JSONException {
        API api = new API();
        Boolean response = api.login("admin", "1");
        System.out.println("[?] Correct assert equals in order to avoid inserting testing data");
        assertEquals(true, false);
        Crime crime = new Crime(4.4444,
                4.4444,
                "Test title",
                "2020-05-22",
                5,
                "Example report");

        response = api.addCrime(crime);
        if (!response){
            System.out.println("[!] " + api.getError());
        }

        assertEquals(true, response);


    }

    @Test
    public void check_object_encodebase64() throws IOException {


    }
}