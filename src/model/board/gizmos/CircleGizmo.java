package model.board.gizmos;

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
    private BoardObjectType type = BoardObjectType.CIRCLE;
    private String name;
    private List<Observer> observers;

    public CircleGizmo(double x, double y, double diameter, String name) {
        this.x = x;
        this.y = y;

        this.name = name;

        this.diameter = diameter;
        rCoefficient = 1.0;
        observers = new ArrayList<>();
    }

    @Override
    public List<LineSegment> getLines() {
        return new ArrayList<>();
    }

    @Override
    public List<Circle> getCircles() {
        Circle circle = new Circle(x+diameter/2,y+diameter/2,diameter/2);
        List<Circle> circles = new ArrayList<>();
        circles.add(circle);
        return circles;
    }

    @Override
    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getRCoefficient() {
        return rCoefficient;
    }

    @Override
    public void rotate() {
    }

    @Override
    public double getAngle() {
        return 0;
    }

    @Override
    public void trigger() {
    }

    @Override
    public void sendTrigger() {
        //TODO: implement when making triggering system
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
    public BoardObjectType getType() {
        return type;
    }

    @Override
    public Vect getCenter() {
        return new Vect(x+(diameter/2), y+(diameter/2));
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
