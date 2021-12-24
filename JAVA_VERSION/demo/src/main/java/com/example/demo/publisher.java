package com.example.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class publisher {
    private enum EXCHANGE_TYPE {DIRECT, FANOUT, TOPIC, HEADERS}
    private final static String EXCHANGE_NAME = "hello";
    private final static String TOPIC_KEY_NAME = ""; // Used in topic/direct exchanges. Format is keyword1.keyword2...
    private final static String message = "Hello World!";

    public static void main(String[] argv) throws Exception {
        // Connect to the RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("152.71.155.95");
        factory.setUsername("student");
        factory.setPassword("COMP30231");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //channel.exchangeDelete(EXCHANGE_NAME); // sometimes you must delete an existing exchange

            // Declare the exchange you want to connect your queue to
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE.FANOUT.toString().toLowerCase());

            channel.basicPublish(EXCHANGE_NAME,
                    TOPIC_KEY_NAME, // This param is for the routing key, usually used for direct/ topic queues.
                    new AMQP.BasicProperties.Builder()
                            .contentType("text/plain")
                            .deliveryMode(2)
                            .priority(1)
                            .userId("student")
                            //.expiration("60000")
                            .build(),
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + TOPIC_KEY_NAME + ":" + message + "'");
        }
    }
}
