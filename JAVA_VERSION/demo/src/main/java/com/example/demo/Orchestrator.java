package com.example.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class Orchestrator {
    @GetMapping("/orchestrator")
    public Serializable main(@RequestParam("service") String service) throws IOException {
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

    // http://localhost:8080/api/orchestrator/submitTrip?userID=12345&location=London&date=20211230
    @GetMapping("/orchestrator/submitTrip")
    public void submit_trip_proposal(@RequestParam("userID") String userID,
                                     @RequestParam("location") String location,
                                     @RequestParam("date") int date)
            throws IOException, TimeoutException, JSONException {
        /* Submit trip proposal message (use the exchange called TRAVEL_OFFERS):
        notify other users about a trip proposal. The message should contain the user
        ID (sender or receiver), the message ID, coordinates/name of the place of
        visit, and the proposed trip date no more than 14 days in the future. */

        // Create JSON object
        JSONObject JSON_message = new JSONObject();
        JSON_message.put("userID", userID);
        JSON_message.put("messageID", id_service.get_ID());
        JSON_message.put("location", location);
        JSON_message.put("date", date);

        // Publish client's message
        publisher new_publisher = new publisher("TRAVEL_OFFERS", "topic_name", JSON_message);
        new_publisher.publish();
    }

    @GetMapping("/orchestrator/queryMessage")
    public void query_message() throws IOException, TimeoutException, JSONException {
        /* Query message (use the exchange called TRAVEL_OFFERS): retrieve
        information about upcoming trips. The response should contain the user
        ID, the message ID, coordinates of the place of visit, and the
        proposed trip date no more than 14 days in the future. In addition, when
        relaying it to the client after a REST call, the service should append
        the weather forecast for the location at the specified date. */

        subscriber new_subscriber = new subscriber("TRAVEL_OFFERS", "topic_name", "123");
        String message =  new_subscriber.main(true);
    }

    @GetMapping("/orchestrator/intentMessage")
    public void intent_message(@RequestParam("userID") String userID,
                               @RequestParam("proposed_userID") String proposed_userID)
            throws IOException, TimeoutException, JSONException {
        System.out.println(proposed_userID);
        /* Intent message (use the exchange called TRAVEL_INTENT): notify a user
        who has published a trip proposal that another user is interested in the invite.
        The message should contain the user ID, the ID of the user that has
        submitted the proposal, and the message ID */

        // Create JSON object
        JSONObject JSON_message = new JSONObject();
        JSON_message.put("userID", userID);
        JSON_message.put("proposed_userID", proposed_userID);
        JSON_message.put("messageID", id_service.get_ID());

        // Publish client's message
        publisher new_publisher = new publisher("TRAVEL_INTENT", proposed_userID, JSON_message);
        new_publisher.publish();
    }

    @GetMapping("/orchestrator/checkIntent")
    public Serializable check_intent_message(@RequestParam("userID") String userID) throws IOException, TimeoutException, JSONException {
        System.out.println(userID);
        /* Check intent message (use the exchange called TRAVEL_ INTENT): retrieve
        information about other users’ interest in the user’s trip proposal. The service
        response to a client REST call client should contain all the information sent in the Intent messages. */

        String queue_name = UUID.randomUUID().toString();
        subscriber new_subscriber = new subscriber("TRAVEL_INTENT", userID, queue_name);
        String message =  new_subscriber.main(false);

        System.out.println("Orchestrator received" + message);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        return map;
    }
}