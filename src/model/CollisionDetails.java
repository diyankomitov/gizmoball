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

    public double getTimeUntilCollision(Ball ball) {
        return details.get(ball);
    }

    public void addBall(Ball ball) {
        System.out.println("added " + ball);
        details.put(ball, Double.MAX_VALUE);
    }

    public void removeBall(Ball ball){
        System.out.println("removed " + ball);
        details.remove(ball);
    }
}
