package com.example.library.entity;

public class Book {

    private int id;
    private String title;
    private String author;
    private boolean presence;

    public Book(String title, String author, boolean presence) {
        this.title = title;
        this.author = author;
        this.presence = presence;
    }

    public Book() {
    }

    public Book(int id, String title, String author, boolean presence) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.presence = presence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

}
