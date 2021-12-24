package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class Orchestrator {
    @GetMapping("/orchestrator")
    public Serializable main(@RequestParam("service") String service) {
        HashMap<String, String> map = new HashMap<>();

        switch (service) {
            case "ID":
                map.put("id", id_service.get_ID());
                return map;
            case "weather":
                map.put("temp", weather_service.get_weather());
                return map;
            default:
                return "we do not offer that service";
        }
    }

    public void query_message() {
        /* Query message (use the exchange called TRAVEL_OFFERS): retrieve
        information about upcoming trips. The response should contain the user
        ID, the message ID, coordinates of the place of visit, and the
        proposed trip date no more than 14 days in the future. In addition, when
        relaying it to the client after a REST call, the service should append
        the weather forecast for the location at the specified date. */
    }

    public void submit_trip_proposal() throws IOException, TimeoutException {
        /* Submit trip proposal message (use the exchange called TRAVEL_OFFERS):
        notify other users about a trip proposal. The message should contain the user
        ID (sender or receiver), the message ID, coordinates/name of the place of
        visit, and the proposed trip date no more than 14 days in the future. */

        publisher.publish("hello", "", "");

    }
}