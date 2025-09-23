package com.booky.demo.service;

import com.booky.demo.dao.UserDAO;
import com.booky.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public Integer register(User user) {
        Integer id = userDAO.getIdByUsername(user.getUsername());
        if(id == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); //encode password
            id = userDAO.register(user);    //register user
            return id;
        }
        return -1;
    }

    @Transactional
    public Integer login(User user) {
        Integer id = userDAO.getIdByUsername(user.getUsername());
        if(id == null)
            return -1; //username not found

        String storedHash = userDAO.getPasswordHashById(id);
        if(storedHash == null)
            return -1; //extra check, idk why

        if (!passwordEncoder.matches(user.getPassword(), storedHash))
            return -2; // password incorrect

        return id;
    }
}
