package model.board.gizmos;

import model.board.BoardObjectType;
import physics.*;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class TriangleGizmo implements Gizmo {
    private double x;
    private double y;
    private final double side;
    private final double rCoefficient;
    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private BoardObjectType type = BoardObjectType.TRIANGLE;
    private double angle = 0;
    private String name;
    private List<Observer> observers;

    public TriangleGizmo(double x, double y, double side, String name) {
        this.x = x;
        this.y = y;
        this.side = side;

        this.name = name;

        rCoefficient = 1.0;
        sides = new ArrayList<>();
        corners = new ArrayList<>();
        observers = new ArrayList<>();
    }

    @Override
    public List<LineSegment> getLines() {
        sides.clear();

        LineSegment ls1 = new LineSegment(x, y, x, y+side);
        LineSegment ls2 = new LineSegment(x, y+side,x+side, y+side);
        LineSegment ls3 = new LineSegment(x+side,y+side,x, y);

        ls1 = Geometry.rotateAround(ls1, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));
        ls2 = Geometry.rotateAround(ls2, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));
        ls3 = Geometry.rotateAround(ls3, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));

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

        c1 = Geometry.rotateAround(c1, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));
        c2 = Geometry.rotateAround(c2, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));
        c3 = Geometry.rotateAround(c3, new Vect(x + side/2, y + side/2), new Angle(Math.toRadians(angle)));


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
    public BoardObjectType getType() {
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

    @Override
    public boolean isRotating() {
        return false;
    }

    @Override
    public Vect getCenter() {
        return new Vect(x+(side/2), y+(side/2));
    }

    @Override
    public double getAngularVelocity() {
        return 0;
    }

    public void rotate(){
        angle += 90;
        if(angle>=360){
            angle -= 360;
        }
    }

    public double getAngle(){
        return angle;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}