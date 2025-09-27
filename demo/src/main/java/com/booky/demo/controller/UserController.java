package com.booky.demo.controller;

import com.booky.demo.dto.UserDTO;
import com.booky.demo.model.User;
import com.booky.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody User user) {
        Optional<Integer> userId = userService.register(user);

        if(userId.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username is not available");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userId.get());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<Integer> userId = userService.login(user);

        if(userId.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Username or Password!");

        System.out.println("Id is "+ userId.get());
        return ResponseEntity.ok(userId.get());
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("Logged out successfully");
    }
}
