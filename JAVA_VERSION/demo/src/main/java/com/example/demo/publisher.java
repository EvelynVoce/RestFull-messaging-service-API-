package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;

public class publisher {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private static String EXCHANGE_NAME;
    private static String TOPIC_KEY_NAME;
    private static String MESSAGE;

    public publisher(String exchange_name, String topic_key_name, String message) {
        this.EXCHANGE_NAME = exchange_name;
        this.TOPIC_KEY_NAME = topic_key_name;
        this.MESSAGE = message;
    }

    public static void publish() throws IOException, TimeoutException {
        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(EXCHANGE_NAME, publisher.EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.basicPublish(EXCHANGE_NAME,
                TOPIC_KEY_NAME, // This param is for the routing key, usually used for direct/ topic queues.
                new AMQP.BasicProperties.Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .userId("student")
                        //.expiration("60000")
                        .build(),
                MESSAGE.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + TOPIC_KEY_NAME + ":" + MESSAGE + "'");
    }
}
