package com.example.saga_pattern.service;

import com.example.saga_pattern.step.Step;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserSaga {
    private final List<Step> steps;

    public UserSaga(List<Step> steps) {
        this.steps = steps;
    }

    public void execute() {
        for (Step step : steps) {
            try {
                step.execute();
            } catch (Exception e) {
                compensate(step);
                break;
            }
        }
    }

    private void compensate(Step failedStep) {
        int failedStepIndex = steps.indexOf(failedStep);
        List<Step> stepsToCompensate = steps.subList(0, failedStepIndex);
        Collections.reverse(stepsToCompensate);
        for (Step step : stepsToCompensate) {
            step.compensate();
        }
    }
}
