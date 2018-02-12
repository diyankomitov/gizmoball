package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class SquareGizmo implements Gizmo {
    private double x;
    private double y;
    private double width;
    private final double rCoefficient;
    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private GizmoType type = GizmoType.SQUARE;

    private String name;

    public SquareGizmo(double x, double y, double width, String name) {
        this.x = x;
        this.y = y;

        this.name = name;

        this.width = width;

        rCoefficient = 1.0;

        sides = new ArrayList<>();
        corners = new ArrayList<>();
    }

    @Override
    public List<LineSegment> getLines() {
        sides.clear();

        LineSegment ls1 = new LineSegment(x,y,x+width, y);
        LineSegment ls2 = new LineSegment(x+width,y,x+width, y+width);
        LineSegment ls3 = new LineSegment(x+width,y+width,x, y+width);
        LineSegment ls4 = new LineSegment(x,y+width,x, y);
        sides.add(ls1);
        sides.add(ls2);
        sides.add(ls3);
        sides.add(ls4);

        return sides;
    }

    @Override
    public List<Circle> getCircles() {
        corners.clear();

        Circle c1 = new Circle(x, y, 0);
        Circle c2 = new Circle(x+width, y, 0);
        Circle c3 = new Circle(x+width, y+width, 0);
        Circle c4 = new Circle(x, y+width, 0);
        corners.add(c1);
        corners.add(c2);
        corners.add(c3);
        corners.add(c4);

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
