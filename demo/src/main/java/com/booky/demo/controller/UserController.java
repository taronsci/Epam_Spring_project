package com.booky.demo.controller;

import com.booky.demo.model.User;
import com.booky.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    //check if this works, password hashing part!
    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody User user) {
        System.out.println("signing up!");
        Integer userId = userService.register(user);
        if(userId == -1)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username is not available");

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    //check if this works, password hashing !
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Integer userId = userService.login(user);
        if(userId == -1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not registered");
        else if(userId == -2)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not registered");

        return ResponseEntity.ok(userId);
    }
}
