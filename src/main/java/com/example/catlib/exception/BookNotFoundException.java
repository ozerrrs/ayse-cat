package com.example.catlib.exception;

public class BookNotFoundException extends RuntimeException {

    private final String topic;

    public BookNotFoundException(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}