package model.board.gizmos;

import model.BoundingBox;
import model.board.BoardObjectType;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class SquareGizmo implements Gizmo {
    private double x;
    private double y;
    private double width;
    private final double rCoefficient;
    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private BoardObjectType type;

    private String name;
    private List<Observer> observers;

    private boolean triggered;
    private double angle;

    public SquareGizmo(double x, double y, double width, String name) {
        this.x = x;
        this.y = y;

        this.name = name;

        this.width = width;

        rCoefficient = 1.0;

        sides = new ArrayList<>();
        corners = new ArrayList<>();
        observers = new ArrayList<>();

        triggered = false;
        angle = 0;
        type = BoardObjectType.SQUARE;
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
    public Vect getCenter() {
        return new Vect(x + (width/2), y + (width/2));
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
    public void trigger(boolean keyPressed) {
        triggered = true;
    }

    @Override
    public void sendTrigger() {
        //TODO: implement this when we are adding collision
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, x+width, y+width);
    }

    @Override
    public void getTimeUntilCollision() {

    }

    @Override
    public void activateAction() {

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
