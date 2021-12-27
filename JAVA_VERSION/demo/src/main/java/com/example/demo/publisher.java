package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import org.json.JSONObject;

public class publisher {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private static String EXCHANGE_NAME;
    private static String TOPIC_KEY_NAME;
    private static JSONObject JSON_message;

    public publisher(String exchange_name, String topic_key_name, JSONObject message) {
        EXCHANGE_NAME = exchange_name;
        TOPIC_KEY_NAME = topic_key_name;
        JSON_message = message;
    }

    public static void publish() throws IOException, TimeoutException {
        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(EXCHANGE_NAME, publisher.EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.basicPublish(EXCHANGE_NAME,
                TOPIC_KEY_NAME, // This param is for the routing key, usually used for direct/ topic queues.
                new AMQP.BasicProperties.Builder().deliveryMode(2).priority(1).build(),
                JSON_message.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + TOPIC_KEY_NAME + ":" + JSON_message.toString().getBytes() + "'");
    }
}
