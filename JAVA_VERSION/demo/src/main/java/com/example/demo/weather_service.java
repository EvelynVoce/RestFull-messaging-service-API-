package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/api/orchestrator?service=weather (just used for quick testing)
public class weather_service {
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
