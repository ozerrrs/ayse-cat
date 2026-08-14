package com.example.catlib.exception;

public class CatNotFoundException extends RuntimeException {

    private final String tag;

    public CatNotFoundException(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}