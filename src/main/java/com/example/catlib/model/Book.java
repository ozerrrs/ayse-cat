package com.example.catlib.model;

public class Book {

    private String title;
    private String author;
    private Integer firstPublishYear;
    private String openLibraryUrl;

    public Book() {}

    public Book(String title, String author, Integer firstPublishYear, String openLibraryUrl) {
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.openLibraryUrl = openLibraryUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    public void setFirstPublishYear(Integer firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    public String getOpenLibraryUrl() {
        return openLibraryUrl;
    }

    public void setOpenLibraryUrl(String openLibraryUrl) {
        this.openLibraryUrl = openLibraryUrl;
    }
}