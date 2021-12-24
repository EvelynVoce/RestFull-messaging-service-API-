/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scc.external.service1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class SCCExternalService2 {
    
    public static void main(String[] args) throws MalformedURLException {
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
        for( Map.Entry<String, String> entry : parameters.entrySet()) {
            params_str += (entry.getKey() + "=" + entry.getValue() + "&");
        }
        String ID = gen_ID(params_str);
        System.out.println(ID);
    }


    public static String gen_ID(String params_str) throws MalformedURLException {
        String url_str = "https://www.random.org/strings/?";
        URL url = new URL(url_str + params_str);
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            
            BufferedReader reader;
            String line;
            StringBuffer responseContent = new StringBuffer();
            reader = new BufferedReader (new InputStreamReader(con.getInputStream()));
            while ((line =  reader.readLine()) != null){
                responseContent.append(line);
            }
            con.disconnect();
            return responseContent.toString();

        } catch (IOException ex) {
            Logger.getLogger(SCCExternalService1.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
