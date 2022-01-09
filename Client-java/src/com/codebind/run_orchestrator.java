package com.codebind;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class run_orchestrator {
    public static String get_id() throws IOException {
        String response = main_page.get_response.main("http://localhost:8080/api/orchestrator");
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

    public static String query_proposal_func() throws IOException {
        String response = main_page.get_response.main("http://localhost:8080/api/orchestrator/queryMessage");
        JSONObject full_message = new JSONObject(response);
        JSONObject message = new JSONObject(full_message.getString("message"));
        System.out.println("Client received proposal" + message);

        JSONObject json_weather = message.getJSONObject("weather");
        JSONObject json_temps = json_weather.getJSONObject("temp2m");

        String txt = "Proposal: " + message.get("userID") + ". Wants to go to " + message.get("location") + " on "
                + message.get("date") + ". The weather will be " + json_weather.getString("weather") +
                " with lowest temperatures of " + json_temps.getInt("min") + " and highs of " +
                json_temps.getInt("max") + " degrees celsius";
        return txt;
    }

    public static void submit_intent(String user_id, String proposed_user_id) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userID", user_id);
        parameters.put("proposed_userID", proposed_user_id);

        // Convert parameters to String
        String params_str = "";
        for( Map.Entry<String, String> entry : parameters.entrySet())
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");
        main_page.get_response.main("http://localhost:8080/api/orchestrator/intentMessage?" + params_str);
    }


    public static String check_intent(String user_id) throws IOException {
        String response = main_page.get_response.main(
                "http://localhost:8080/api/orchestrator/checkIntent?userID=" + user_id);
        JSONObject full_message = new JSONObject(response);
        JSONObject message = new JSONObject(full_message.getString("message"));
        System.out.println("Client received intent" + message);
        String userID = message.getString("userID");
        return "Intent: " + userID +" , would like to join you on your trip";
    }
}
