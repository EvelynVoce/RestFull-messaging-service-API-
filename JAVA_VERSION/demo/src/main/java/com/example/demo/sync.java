package com.example.demo;

// https://stackoverflow.com/questions/6847870/return-string-from-a-callback-java (Found here)
public class sync {
    private static final long TIMEOUT = 20000L;
    private String result;

    public String getResult() {
        long startTimeMillis = System.currentTimeMillis();
        while (result == null && System.currentTimeMillis() - startTimeMillis < TIMEOUT) {
            synchronized (this) {
                try {
                    wait(TIMEOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        synchronized (this) {
            notify();
        }
    }
}
