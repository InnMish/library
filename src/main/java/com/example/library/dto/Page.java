package com.example.library.dto;

import com.example.library.entity.Book;

import java.util.List;

public class Page {
    private List<Book> books;
    private Integer count;

    public Page(List<Book> books, Integer count) {
        this.books = books;
        this.count = count;
    }

    public Page() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
