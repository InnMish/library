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
    public List<Book> findAll(int limit, int offset) {
        Map<String, Integer> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return namedParameterJdbcTemplate.query("select * from book limit :limit offset :offset", params, (resultSet, i) -> {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            boolean presence = resultSet.getBoolean("presence");
            return new Book(id, title, author, presence);
        });
    }

    @Override
    public Integer length() {
        return namedParameterJdbcTemplate.queryForObject("select count(*) from book", new HashMap<>(), Integer.class);
    }

    @Override
    @Transactional
    public void addOrEditBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("presence", book.isPresence());
        if (book.getId() > 0) {
            namedParameterJdbcTemplate
                    .update("update book set title = :title, author = :author, presence = true where id = :id", params);
        } else {
            namedParameterJdbcTemplate
                    .update("insert into book (title, author, presence) values (:title, :author, true)", params);
        }
    }

    @Override
    public void deleteBookById(int id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        int status = namedParameterJdbcTemplate.update("delete from book where id = :id", params);
        if (status != 0) {
            System.out.println("Book data deleted for ID " + id);
        } else {
            System.out.println("No Book found with ID " + id);
        }
    }

    @Override
    public void takeBook(int id) {
        Book book = getBook(id);
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        if (book != null && book.isPresence()) {
            namedParameterJdbcTemplate.update("update book set presence = false where id = :id", params);
            System.out.println("Book " + id + " was taken");
        } else {
            System.out.println("No book found");
        }
    }

    @Override
    public void returnBook(int id) {
        Book book = getBook(id);
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        if (book != null && !book.isPresence()) {
            namedParameterJdbcTemplate.update("update book set presence = true where id = :id", params);
            System.out.println("Book " + id + " was taken");
        } else {
            System.out.println("Book is already here");
        }
    }

    @Override
    public Book getBook(int id) {
        return namedParameterJdbcTemplate.query("select * from book where id=" + id, resultSet -> {
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPresence(resultSet.getBoolean("presence"));
                return book;
            }

            return null;
        });
    }
}
