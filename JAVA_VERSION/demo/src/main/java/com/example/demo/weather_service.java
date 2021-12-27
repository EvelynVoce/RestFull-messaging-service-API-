package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/api/orchestrator?service=weather (just used for quick testing)
public class weather_service {
    public static String get_weather() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", "-1.15");
        parameters.put("lat", "52.95");
        parameters.put("lang", "en");
        parameters.put("product", "civillight");
        parameters.put("unit", "metric");
        parameters.put("output", "json");

        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet()) {
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");
        }

        String url_str = "https://www.7timer.info/bin/astro.php?" + params_str;
        return get_response.main(url_str);
    }
}
