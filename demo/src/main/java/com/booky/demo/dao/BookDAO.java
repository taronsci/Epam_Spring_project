package com.booky.demo.dao;

import com.booky.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Book book) {
        String sql = "INSERT INTO book(title, author, year, genre) VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setString(4, book.getGenre());
            return ps;
        }, keyHolder);
        Number key = (Number) keyHolder.getKeys().get("id");

        return key.intValue();
    }

    public boolean findById(Integer bookId){
        String sql = "SELECT COUNT(*) FROM book WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
        return count != null && count > 0;
    }

    public Integer findByTitleAndAuthor(String title, String author){
        String sql = "SELECT id FROM book WHERE title = ? AND author = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, title, author);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("title author not found.");
            return null;
        }
    }
    public Book findBookById(Integer bookId){
        String sql = "SELECT * FROM book WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), bookId);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("id not found.");
            return null;
        }
    }

}
