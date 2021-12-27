package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import org.json.JSONException;
import org.json.JSONObject;

public class test_pub {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private static String EXCHANGE_NAME = "TRAVEL_OFFERS";
    private static String TOPIC_KEY_NAME = "topic_name";
    private static JSONObject JSON_message;

    public static void main(String[] argv) throws IOException, TimeoutException, JSONException {

        JSON_message = new JSONObject();
        JSON_message.put("userID", "12345678");
        JSON_message.put("messageID", "66642069");
        JSON_message.put("location", "Alabama");

        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(EXCHANGE_NAME, com.example.demo.test_pub.EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.basicPublish(EXCHANGE_NAME,
                TOPIC_KEY_NAME, // This param is for the routing key, usually used for direct/ topic queues.
                new AMQP.BasicProperties.Builder().contentType("text/plain")
                        .deliveryMode(2).priority(1)
                        .userId("student")//.expiration("60000")
                        .build(),
                JSON_message.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + TOPIC_KEY_NAME + ":" + JSON_message.toString().getBytes(StandardCharsets.UTF_8) + "'");
    }
}
