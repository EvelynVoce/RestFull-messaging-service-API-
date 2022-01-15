package com.codebind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class get_response
{
    public static String main(String url_str) throws IOException {
        URL url = new URL(url_str);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        con.disconnect();
        return responseContent.toString();
    }
}