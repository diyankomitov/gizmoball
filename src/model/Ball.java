package model;

import physics.Circle;
import physics.Vect;

public class Ball {
    private final double diameter;
    private Vect velocity;
    private String id = "B";
    private double x;
    private double y;

    public Ball(double x, double y, double xv, double yv, int counter) {
        this.x = x;
        this.y = y;
        this.velocity = new Vect(xv, yv);
        this.diameter = 0.5;
        this.id = id.concat(Integer.toString(counter));
    }

    public Circle getCircle() {
        return new Circle(x,y,diameter/2);
    }

    public Vect getVelocity() {
        return velocity;
    }

    public Vect getCenter() {
        return new Vect(x,y);
    }

    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }

    public void moveForTime(double moveTime) {
        x += (velocity.x() * moveTime);
        y += (velocity.y() * moveTime);
    }

    public String getId(){
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}