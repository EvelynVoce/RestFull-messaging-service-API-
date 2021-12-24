package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class weather_service {

    public static String get_weather() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", "-1.15");
        parameters.put("lat", "52.95");
        parameters.put("lang", "en");
        parameters.put("unit", "metric");
        parameters.put("output", "json");

        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet()) {
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");
        }

        String url_str = "https://www.7timer.info/bin/astro.php?" + params_str;
        try {
            return get_response.main(url_str).toString();
        } catch (IOException ex) {
            Logger.getLogger(weather_service.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

}
