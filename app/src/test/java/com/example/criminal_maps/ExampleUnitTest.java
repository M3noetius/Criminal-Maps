package com.example.criminal_maps;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.criminal_maps.NetworkComms.Network;

import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println("yoo");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void check_login() throws JSONException {
        Network net = new Network();
        HashMap<String, String> jsonHashmap = net.login("admin", "1");
        for (String key : jsonHashmap.keySet()) {
            System.out.println(key + " : " + jsonHashmap.get(key));
        }
        assertEquals(1, 1);
    }

    @Test
    public void check_register() throws JSONException {
        Network net = new Network();
        HashMap<String, String> jsonHashmap = net.register("admin", "1");
        for (String key : jsonHashmap.keySet()) {
            System.out.println(key + " : " + jsonHashmap.get(key));
        }
        assertEquals(1, 1);
    }

    @Test
    public void check_json_decode() throws JSONException {
        HashMap<String, String> jsonHashmap = Network.decodeJson("{'name' : 'admin'}");
        for (String key : jsonHashmap.keySet()) {
            System.out.println(key + " : " + jsonHashmap.get(key));
        }

    }
}