package com.codebind;

import java.io.IOException;
import org.json.JSONObject;

public class run_orchestrator {
    public static String main() throws IOException {
        String response = main_page.get_response.main("http://localhost:8080/api/orchestrator?service=ID");
        JSONObject json_message = new JSONObject(response);
        return json_message.getString("id");
    }

}
