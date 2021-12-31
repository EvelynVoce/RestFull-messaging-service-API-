package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import org.json.JSONObject;

public class publisher {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}

    public static void publish(String exchange_name, String topic_key_name, JSONObject message) throws IOException, TimeoutException {
        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(exchange_name, EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.basicPublish(exchange_name,
                topic_key_name, // This param is for the routing key, usually used for direct/ topic queues.
                new AMQP.BasicProperties.Builder().deliveryMode(2).priority(1).build(),
                message.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + topic_key_name + ":" + message.toString() + "'");
    }
}
