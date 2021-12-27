package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/api/orchestrator?service=ID (just used for quick testing)
public class id_service {
    public static String get_ID() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("num", "1");
        parameters.put("len", "8");
        parameters.put("digits", "on");
        parameters.put("upperalpha", "on");
        parameters.put("loweralpha", "on");
        parameters.put("unique", "on");
        parameters.put("format", "plain");
        parameters.put("rnd", "new");

        // Convert parameters to String
        String params_str = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");
        }

        return get_response.main("https://www.random.org/strings/?" + params_str);
    }
}