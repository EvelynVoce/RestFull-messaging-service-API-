package com.example.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class establish_connection {

    public static Channel main() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("152.71.155.95");
        factory.setUsername("student");
        factory.setPassword("COMP30231");
//
//        factory.setHost("localhost");
//        factory.setUsername("guest");
//        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
