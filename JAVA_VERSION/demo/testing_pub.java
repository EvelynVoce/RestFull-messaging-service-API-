package com.example.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class testing_pub {
    public static void main() throws IOException, TimeoutException {
        publisher pub = new publisher("hello", "", "");
        pub.publish();
        System.out.println("");
    }
}