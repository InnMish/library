package com.example.library.dao;

import com.example.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BookDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcTemplate.query("select * from book", (resultSet, i) -> {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            boolean presence = resultSet.getBoolean("presence");
            return new Book(id, title, author, presence);
        });
    }

    @Override
    @Transactional
    public int addBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("presence", book.isPresence());
        return namedParameterJdbcTemplate
                .update("insert into book (title, author, presence) values (:title, :author, :presence)", params);
    }

    @Override
    public void updateBook(Book book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("presence", book.isPresence())
                .addValue("id", book.getId());
        int status = namedParameterJdbcTemplate
                .update("update book set title=:title, author = :author, presence = :presence where id = :id", params);
        if (status != 0) {
            System.out.println("Book data updated for ID " + book.getId());
        } else {
            System.out.println("No Book found with ID " + book.getId());
        }
    }

    @Override
    public void deleteBookById(Book book) {
        SqlParameterSource params = new MapSqlParameterSource("id", book.getId());
        int status = namedParameterJdbcTemplate.update("delete from book where id = :id", params);
        if (status != 0) {
            System.out.println("Book data deleted for ID " + book.getId());
        } else {
            System.out.println("No Book found with ID " + book.getId());
        }
    }

    @Override
    public void takeBook(Book book) {
        SqlParameterSource params = new MapSqlParameterSource("id", book.getId());
        if (book.isPresence()) {
            namedParameterJdbcTemplate.update("update book set presence = false where id = :id", params);
            System.out.println("Book " + book.getId() + " was taken");
        } else {
            System.out.println("No book found");
        }
    }

    @Override
    public void returnBook(Book book) {
        SqlParameterSource params = new MapSqlParameterSource("id", book.getId());
        if (!book.isPresence()) {
            namedParameterJdbcTemplate.update("update book set presence = true where id = :id", params);
            System.out.println("Book " + book.getId() + " was taken");
        } else {
            System.out.println("Book is already here");
        }
    }
}
