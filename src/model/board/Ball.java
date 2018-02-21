package model.board;

import physics.Circle;
import physics.Vect;
import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class Ball implements BoardObject, Observable{
    private final double diameter;
    private Vect velocity;
    private Vect potentialVelocity;
    private double x;
    private double y;
    private boolean inAbsorber = false;
    private String name;
    private List<Observer> observers = new ArrayList<>();

    public Ball(double x, double y, double xv, double yv, String name) {
        this.x = x;
        this.y = y;
        this.name=name;
        this.velocity = new Vect(xv, yv);
        this.diameter = 0.5;
        potentialVelocity = new Vect(0,0);
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

//        System.out.println("x: " + x + " y: " + y);
        notifyObservers();
    }

    public void setPotentialVelocity(Vect potentialVelocity) {
        this.potentialVelocity = potentialVelocity;
    }

    public void applyPotentialVelocity() {
        velocity = potentialVelocity;
    }

    public String getName(){
        return name;
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

    @Override
    public BoardObjectType getType() {
        return BoardObjectType.BALL;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
