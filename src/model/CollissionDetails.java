package model;

import java.util.HashMap;
import java.util.Map;

public class CollissionDetails {
    private Map<Ball, Double> details;

    public CollissionDetails() {
        this.details = new HashMap<>();
    }

    public void setTimeUntilCollission(Ball ball, double timeUntilCollission) {
        details.put(ball, timeUntilCollission);
    }

    public double getTimeUntilCollission(Ball ball) {
        return details.get(ball);
    }

    public void addBall(Ball ball) {
        details.put(ball, Double.MAX_VALUE);
    }
}
