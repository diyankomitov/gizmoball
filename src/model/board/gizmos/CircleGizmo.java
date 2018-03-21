package model.board.gizmos;

import model.BoundingBox;
import model.board.BoardObjectType;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class CircleGizmo implements Gizmo {
    private double x;
    private double y;
    private final double diameter;
    private final double rCoefficient;
    private final List<Circle> circles;
    private final BoardObjectType type;
    private final String name;
    private final List<Observer> observers;
    private double angle;
    private boolean triggered;

    public CircleGizmo(double x, double y, double diameter, String name) {
        this.x = x;
        this.y = y;

        this.name = name;

        this.diameter = diameter;
        rCoefficient = 1.0;
        circles = new ArrayList<>();
        observers = new ArrayList<>();

        triggered = false;
        angle = 0;
        type = BoardObjectType.CIRCLE;
    }

    @Override
    public List<LineSegment> getLines() {
        return new ArrayList<>();
    }

    @Override
    public List<Circle> getCircles() {
        circles.clear();
        Circle circle = new Circle(x+diameter/2,y+diameter/2,diameter/2);
        circles.add(circle);
        return circles;
    }

    @Override
    public Vect getCenter() {
        return new Vect(x + (diameter/2), y + (diameter/2));
    }

    @Override
    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
        notifyObservers();
    }

    @Override
    public double getRCoefficient() {
        return rCoefficient;
    }

    @Override
    public void rotate() {
        angle = 0;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public void trigger(boolean keyPressed, boolean keyReleased) {
        triggered = !triggered;
    }

    @Override
    public void sendTrigger() {
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, x+diameter, y+diameter);
    }

    @Override
    public void getTimeUntilCollision() {

    }

    @Override
    public void activateAction() {
        notifyObservers();

    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public BoardObjectType getType() {
        return type;
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
    public List<Observer> getObservers() {
        return observers;
    }
}
