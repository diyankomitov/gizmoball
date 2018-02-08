package model;

import com.sun.org.apache.xpath.internal.SourceTree;
import physics.Circle;
import physics.Vect;
import util.Observable;

public class Ball implements Observable{
    private final double diameter;
    private Vect velocity;
    private double x;
    private double y;
    private boolean inAbsorber = false;

    public Ball(double x, double y, double xv, double yv) {
        this.x = x;
        this.y = y;
        this.velocity = new Vect(xv, yv);
        this.diameter = 0.5;
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

    public double getDiameter() {
        return diameter;
    }

    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }

    public void moveForTime(double moveTime) {
        x += (velocity.x() * moveTime);
        y += (velocity.y() * moveTime);

        System.out.println("x: " + x + " y: " + y);
        notifyObservers();
    }

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x =  x;
        notifyObservers();
    }

    public double getY() {
        return y;
    }

    public void setY(double y){
        this.y =  y;
        notifyObservers();
    }

    public boolean isInAbsorber() {
        return inAbsorber;
    }

    public void setInAbsorber(boolean inAbsorber) {
        this.inAbsorber = inAbsorber;
    }
}
