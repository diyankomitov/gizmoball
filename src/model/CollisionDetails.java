package model;

import model.board.Ball;

import java.util.HashMap;
import java.util.Map;

public class CollisionDetails {
    private Map<Ball, Double> details;

    public CollisionDetails() {
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