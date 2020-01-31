package com.example.library.dao;

import com.example.library.entity.Book;

import java.util.List;

public interface BookDao {

    public List<Book> findAll(int limit, int offset);

    public Integer length();

    public void addOrEditBook(Book book);

    public void deleteBookById(int id);

    public void takeBook(int id);

    public void returnBook(int id);

    public Book getBook(int id);
}
