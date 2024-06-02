package com.example.saga_pattern.service;

import com.example.saga_pattern.dto.User;
import com.example.saga_pattern.mapper.UserMapper;
import com.example.saga_pattern.step.Step;
import org.springframework.stereotype.Service;

@Service
public class CreateUserStep implements Step {
    private final UserMapper userMapper;
    private User user;

    public CreateUserStep(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void execute(User user) {
        this.user = user;
        userMapper.insert(user);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void compensate() {
        if (user != null) {
            userMapper.delete(user);
        }
    }
}