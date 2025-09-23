package com.booky.demo.dao;

import com.booky.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer register(User user) {

        String sql = "INSERT INTO users (username, email, password) VALUES(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);
        Number key = (Number) keyHolder.getKeys().get("id");

        return key.intValue();
    }

    public Integer findByUsername(String username){
        String sql = "SELECT id FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getPasswordHashById(Integer id){
        String sql = "SELECT password FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
