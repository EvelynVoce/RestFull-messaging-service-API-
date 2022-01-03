package com.codebind;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class run_orchestrator {
    public static String get_id() throws IOException {
        String response = main_page.get_response.main("http://localhost:8080/api/orchestrator?service=ID");
        JSONObject json_message = new JSONObject(response);
        return json_message.getString("id");
    }

    public static void submit_proposal(String user_id, String lat, String lon, String date) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userID", user_id);
        parameters.put("location", "unknown");
        parameters.put("date", date);
        parameters.put("lat", lat);
        parameters.put("lon", lon);

        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet())
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");

        main_page.get_response.main("http://localhost:8080/api/orchestrator/submitTrip?" + params_str);
    }
}
