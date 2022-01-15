package com.example.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/api/orchestrator?service=weather (just used for quick testing)
public class weather_service {

    public static String convert_location(String location) throws IOException, JSONException {


        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", location);
        parameters.put("key", "a6399d45cf9b483fa08085e6958daa05");
        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet())
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");

        String response = get_response.main("https://api.opencagedata.com/geocode/v1/json?" + params_str);
        JSONObject json = new JSONObject(response);
        JSONArray json2 = json.getJSONArray("results");
        JSONObject coords = json2.getJSONObject(0).getJSONObject("bounds");
        JSONObject south_west = coords.getJSONObject("southwest");
        String lat = south_west.getString("lat");
        String lon = south_west.getString("lng");
        return get_weather(lat, lon);
    }


    public static String get_weather(String lat, String lon) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", lon);
        parameters.put("lat", lat);
        parameters.put("lang", "en");
        parameters.put("unit", "metric");
        parameters.put("output", "json");

        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet())
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");

        return get_response.main("https://www.7timer.info/bin/civillight.php?" + params_str);
    }
}

