package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.DeliverCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class subscriber {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private String proposal_message = "DEFAULT";
    private static boolean type;

    public subscriber(boolean query_message){
        type = query_message;
    }

    public void main(String exchange_name, String topic_key_name, String queue_name)
            throws IOException, TimeoutException, JSONException {
        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(exchange_name, EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.queueDeclare(queue_name, true, false, false, null);

        // Link the queue to the exchange
        channel.queueBind(queue_name, exchange_name, topic_key_name); // last param = routing key used for direct/topic
        System.out.println(" [*] Waiting for " + topic_key_name +  " messages. To exit press CTRL+C");

        // This code block indicates a callback which is like an event triggered ONLY when a message is received
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                JSONObject json_message = get_json(message);
                System.out.println(" [x] Subscriber received Query '" + json_message + "'");
                proposal_message = json_message.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        // Consume messages from the queue by using the callback
        channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });
    }

    public static JSONObject get_json(String message) throws JSONException, IOException {
        JSONObject json_message = new JSONObject(message);
        if (!type) return json_message;
        int date = json_message.getInt("date");
        String lat = json_message.getString("lat");
        String lon = json_message.getString("lon");
        String weather = weather_service.get_weather(lat, lon);


        JSONObject json = new JSONObject(weather);
        String jsonString = json.getString("dataseries");
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getInt("date") == date) {
                json_message.put("weather", obj);
                break;
            }
            else System.out.println("Date not found");
        }
        return json_message;
    }

    public String get_stored_message(){
        return proposal_message;
    }
}
