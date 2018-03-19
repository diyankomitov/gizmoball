package model.board;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class Ball implements BoardObject, Observable {
    private double x;
    private double y;
    private final double diameter;
    private final BoardObjectType type;
    private String name;
    private Vect velocity;
    private Vect potentialVelocity;
    private boolean inAbsorber = false;
    private List<Observer> observers = new ArrayList<>();

    public Ball(double x, double y, double xv, double yv, String name) {
        this.x = x;
        this.y = y;
        this.diameter = 0.5;
        this.type = BoardObjectType.BALL;
        this.name = name;
        this.velocity = new Vect(xv, yv);
        this.potentialVelocity = new Vect(0, 0);
    }

    //TODO: FIX MOVEFORTIME
    public void moveForTime(double moveTime) {
        x += (velocity.x() * moveTime);
        y += (velocity.y() * moveTime);
        notifyObservers();
    }

    public void setX(double x) {
        this.x = x;
        notifyObservers();
    }

    public void setY(double y) {
        this.y = y;
        notifyObservers();
    }

    public Vect getVelocity() {
        return velocity;
    }

    public void setVelocity(Vect velocity) {

        double velX = velocity.x();
        double signX = Math.signum(velX);
        double velY = velocity.y();
        double signY = Math.signum(velY);

        velX = Math.max(Math.abs(velX), 0.1);
        velX = Math.min(Math.abs(velX), 200);
        velY = Math.max(Math.abs(velY), 0.1);
        velY = Math.min(Math.abs(velY), 200);

        this.velocity = new Vect(velX * signX, velY * signY);
    }

    public void setPotentialVelocity(Vect potentialVelocity) {
        this.potentialVelocity = potentialVelocity;
    }

    public void applyPotentialVelocity() {
        setVelocity(potentialVelocity);
    }

    public boolean isInAbsorber() {
        return inAbsorber;
    }

    public void setInAbsorber(boolean inAbsorber) {
        this.inAbsorber = inAbsorber;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public Vect getCenter() {
        return new Vect(x, y);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public List<LineSegment> getLines() {
        return new ArrayList<>();
    }

    @Override
    public List<Circle> getCircles() {

        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(x, y, diameter / 2));
        return circles;
    }

    @Override
    public BoardObjectType getType() {
        return type;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
