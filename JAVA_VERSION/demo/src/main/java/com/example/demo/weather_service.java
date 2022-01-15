package com.example.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class weather_service {

    public static String convert_location(String location) throws IOException, JSONException {
        String url = "https://api.opencagedata.com/geocode/v1/json?q=" + location +"&key=a6399d45cf9b483fa08085e6958daa05";
        String response = get_response.main(url);
        JSONArray json = new JSONObject(response).getJSONArray("results");
        JSONObject sw_coords = json.getJSONObject(0).getJSONObject("bounds").getJSONObject("southwest");
        return get_weather(sw_coords.getString("lat"), sw_coords.getString("lng"));
    }


    public static String get_weather(String lat, String lon) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", lon);
        parameters.put("lat", lat);
        parameters.put("lang", "en");
        parameters.put("unit", "metric");
        parameters.put("output", "json");

        // Convert parameters to String
        StringBuilder params_str = new StringBuilder();
        for( Map.Entry<String, String> entry : parameters.entrySet())
            params_str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

        return get_response.main("https://www.7timer.info/bin/civillight.php?" + params_str);
    }
}

