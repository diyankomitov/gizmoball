package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class CircleGizmo implements Gizmo {
    private double x;
    private double y;
    private final double diameter;
    private final double rCoefficient;
    private List<Circle> circles;
    private GizmoType type = GizmoType.CIRCLE;

    private String name = "C";

    public CircleGizmo(double x, double y, double diameter, int cCounter) {
        this.x = x;
        this.y = y;

        this.name= name.concat(Integer.toString(cCounter));

        this.diameter = diameter;

        rCoefficient = 1.0;
        circles = new ArrayList<>();
    }

    @Override
    public List<LineSegment> getLines() {
        return new ArrayList<>();
    }

    @Override
    public List<Circle> getCircles() {
        circles.clear();
        Circle circle = new Circle(x,y,diameter/2);
        circles.add(circle);
        return circles;
    }

    @Override
    public double getRCoefficient() {
        return rCoefficient;
    }

    @Override
    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GizmoType getType() {
        return type;
    }

    @Override
    public double getXCoord() {
        return x;
    }

    @Override
    public double getYCoord() {
        return y;
    }
}