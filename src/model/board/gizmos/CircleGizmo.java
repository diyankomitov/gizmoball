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
    private Circle circle;
    private BoardObjectType type = BoardObjectType.CIRCLE;
    private String name;
    private List<Observer> observers;
    private double angle;
    private boolean triggered;

    public CircleGizmo(double x, double y, double diameter, String name) {
        this.x = x;
        this.y = y;

        this.name = name;

        this.diameter = diameter;
        rCoefficient = 1.0;
        circle = new Circle();
        observers = new ArrayList<>();

        triggered = false;
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
        //TODO
    }

    @Override
    public double getAngle() {
        return 0;
    }

    @Override
    public void trigger() {
        triggered = true;
    }

    @Override
    public void sendTrigger() {
        //TODO: implement this as we implement collision
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
