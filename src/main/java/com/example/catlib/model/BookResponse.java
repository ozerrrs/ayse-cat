package com.example.catlib.model;

import java.util.List;

public class BookResponse {

    private String topic;
    private String openLibraryUrl;
    private List<Book> books;

    public BookResponse() {}

    public BookResponse(String topic, String openLibraryUrl, List<Book> books) {
        this.topic = topic;
        this.openLibraryUrl = openLibraryUrl;
        this.books = books;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOpenLibraryUrl() {
        return openLibraryUrl;
    }

    public void setOpenLibraryUrl(String openLibraryUrl) {
        this.openLibraryUrl = openLibraryUrl;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}