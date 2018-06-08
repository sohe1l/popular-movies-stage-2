package com.example.android.popularmovies_stage2.model;

public class Review {

    private String id;
    private String url;
    private String author;
    private String content;

    public Review(String id, String url, String author, String content) {
        this.id = id;
        this.url = url;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
