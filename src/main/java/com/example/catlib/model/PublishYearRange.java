package com.example.catlib.model;

public class PublishYearRange {

    private Integer from;
    private Integer to;

    public PublishYearRange() {
    }

    public PublishYearRange(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }
}