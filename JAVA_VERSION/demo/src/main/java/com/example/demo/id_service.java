package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

// http://localhost:8080/api/SCC?service=ID (just used for quick testing)
public class id_service {

    public static String get_ID() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("num", "1");
        parameters.put("len", "16");
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

        return gen_ID(params_str);
    }


    public static String gen_ID(String params_str)  {
        String url_str = "https://www.random.org/strings/?" + params_str;
        try {
            return get_response.main(url_str).toString();
        } catch (IOException ex) {
            Logger.getLogger(id_service.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}