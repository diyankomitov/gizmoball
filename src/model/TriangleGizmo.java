package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class TriangleGizmo implements Gizmo {
    private double x;
    private double y;
    private final double side;
    private final double rCoefficient;
    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private GizmoType type = GizmoType.TRIANGLE;

    private String name;

    public TriangleGizmo(double x, double y, double side, String name) {
        this.x = x;
        this.y = y;
        this.side = side;

        this.name = name;

        rCoefficient = 1.0;
        sides = new ArrayList<>();
        corners = new ArrayList<>();
    }

    @Override
    public List<LineSegment> getLines() {
        sides.clear();

        LineSegment ls1 = new LineSegment(x, y, x, y+side);
        LineSegment ls2 = new LineSegment(x, y+side,x+side, y+side);
        LineSegment ls3 = new LineSegment(x+side,y+side,x, y);
        sides.add(ls1);
        sides.add(ls2);
        sides.add(ls3);

        return sides;
    }

    @Override
    public List<Circle> getCircles() {
        corners.clear();

        Circle c1 = new Circle(x, y, 0);
        Circle c2 = new Circle(x, y+side, 0);
        Circle c3 = new Circle(x+side, y+side, 0);
        corners.add(c1);
        corners.add(c2);
        corners.add(c3);

        return corners;
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
