package com.example.saga_pattern.step;

import com.example.saga_pattern.dto.User;

public interface Step {
    void execute(User user);

    void execute();
    void compensate();
}
