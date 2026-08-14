package com.example.catlib.model;

import java.util.List;

public class StoreResponse {

    private String topic;
    private int totalBooks;
    private List<String> bookTitles;
    private PublishYearRange publishYearRange;
    private String catImageUrl;
    private String openLibraryUrl;

    public StoreResponse() {
    }

    public StoreResponse(
            String topic,
            int totalBooks,
            List<String> bookTitles,
            PublishYearRange publishYearRange,

            String catImageUrl,

            String openLibraryUrl
    ) {
        this.topic = topic;
        this.totalBooks = totalBooks;
        this.bookTitles = bookTitles;
        this.publishYearRange = publishYearRange;
        this.catImageUrl = catImageUrl;
        this.openLibraryUrl = openLibraryUrl;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public List<String> getBookTitles() {
        return bookTitles;
    }

    public void setBookTitles(List<String> bookTitles) {
        this.bookTitles = bookTitles;
    }

    public PublishYearRange getPublishYearRange() {
        return publishYearRange;
    }

    public void setPublishYearRange(PublishYearRange publishYearRange) {
        this.publishYearRange = publishYearRange;
    }

    public String getCatImageUrl() {
        return catImageUrl;
    }

  public void setCatImageUrl(String catImageUrl) {
        this.catImageUrl = catImageUrl;
    }

    public String getOpenLibraryUrl() {
        return openLibraryUrl;
    }

    public void setOpenLibraryUrl(String openLibraryUrl) {
        this.openLibraryUrl = openLibraryUrl;
    }
}