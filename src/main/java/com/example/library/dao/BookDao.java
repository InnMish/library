package com.example.library.dao;

import com.example.library.entity.Book;

import java.util.List;

public interface BookDao {

    public List<Book> findAll();

    public int addBook(Book book);

    public void updateBook(Book book);

    public void deleteBookById(Book book);

    public void takeBook(Book book);

    public void returnBook(Book book);
}
