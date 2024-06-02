package com.example.saga_pattern.controller;

import com.example.saga_pattern.dto.User;
import com.example.saga_pattern.service.CreateUserStep;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final CreateUserStep createUserStep;

    public UserController(CreateUserStep createUserStep) {
        this.createUserStep = createUserStep;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            createUserStep.execute(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            createUserStep.compensate();
            return ResponseEntity.status(500).body("User creation failed");
        }
    }
}