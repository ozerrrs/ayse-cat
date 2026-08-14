package com.example.catlib.exception;

public class TopicNotFoundException extends RuntimeException {

    private final String topic;

    public TopicNotFoundException(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}