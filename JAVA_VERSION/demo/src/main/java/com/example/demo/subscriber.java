package com.example.demo;

import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import com.rabbitmq.client.DeliverCallback;

public class subscriber {

    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private static String EXCHANGE_NAME;
    private static String TOPIC_KEY_NAME;
    private static String QUEUE_NAME;

    public subscriber(String exchange_name, String topic_key_name, String queue_name) {
        EXCHANGE_NAME = exchange_name;
        TOPIC_KEY_NAME = topic_key_name;
        QUEUE_NAME = queue_name; // Change this with the unique user ID
    }

    public static String main() throws IOException, TimeoutException {
        Channel channel = establish_connection.main(); // Connect to the RabbitMQ server

        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE.TOPIC.toString().toLowerCase());
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // Link the queue to the exchange
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, TOPIC_KEY_NAME); // last param = routing key used for direct/topic
        System.out.println(" [*] Waiting for " + TOPIC_KEY_NAME +  " messages. To exit press CTRL+C");
        // This code block indicates a callback which is like an event triggered ONLY when a message is received
        final sync syncResult = new sync();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            syncResult.setResult(message);
        };
        // Consume messages from the queue by using the callback
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        return syncResult.getResult();
    }
}
