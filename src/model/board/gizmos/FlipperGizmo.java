package model.board.gizmos;

import model.BoundingBox;
import model.board.BoardObjectType;
import physics.*;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.*;

public class FlipperGizmo implements Gizmo{

    private double x;
    private double y;
    private double xWithOffset;
    private final double width;
    private final double length;
    private final double rCoefficient;
    private ArrayList<LineSegment> sides;
    private ArrayList<Circle> circles;
    private final BoardObjectType type;
    private String name;
    private double angle;
    private Vect pivot;
    private double angularVelocity;
    private boolean triggered;
    private boolean moving;
//    private double offset;
    private List<Observer> observers;
    private boolean flipUp;
    private boolean keyPressed;

    /**
     * Constructor
     * @param x
     * @param y
     * @param a
     */
    public FlipperGizmo(double x, double y, double a, BoardObjectType type, String name){
        width = ONE_L/2;
        length = ONE_L*2;

        double offset = 0;
        if(type == BoardObjectType.RIGHT_FLIPPER) {
            offset = ONE_L+width;
            this.type = type;
        }
        else {
            this.type = BoardObjectType.LEFT_FLIPPER;
        }

        this.x = x;
        this.xWithOffset = x + offset;
        this.y = y;

        this.name=name;

        angle = a;
        sides = new ArrayList<>();
        circles = new ArrayList<>();
        rCoefficient = 0.95;

        angularVelocity = Math.toRadians(FLIPPER_ANGULAR_VELOCITY);

        double radius = width/2;
        pivot = new Vect(this.xWithOffset + radius, this.y + radius);
        observers = new ArrayList<>();


    }

    public double getAngle() {
        return angle;
    }

    @Override
    public void activateAction() {
        flip();
    }

    @Override
    public void trigger(boolean keyPressed) {
        this.keyPressed = keyPressed;
        flipUp = true;
    }

    private void flip() {
        if(type == BoardObjectType.RIGHT_FLIPPER)
        {
            if (flipUp) {
                angle = Math.min(angle + DELTA_ANGLE, 90);
            }
            else {
                angle = Math.max(angle - DELTA_ANGLE, 0);
            }

            if (angle >= 90) {

                flipUp = keyPressed;
            }
        }
        else if (type == BoardObjectType.LEFT_FLIPPER)
        {
            if (flipUp) {
                angle = Math.max(angle - DELTA_ANGLE, -90);
            }
            else {
                angle = Math.min(angle + DELTA_ANGLE, 0);
            }

            if (angle <= -90) {
                flipUp = keyPressed;
            }
        }

        this.notifyObservers();
    }

    @Override
    public void sendTrigger() {
        //TODO: implement when working on the triggering system
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x,y, x+length, y+length);
    }

    @Override
    public void getTimeUntilCollision() {

    }

    public void rotate(){

    }

    @Override
    public List<LineSegment> getLines() {
        double lineLength = length - width;
        double radianAngle = Math.toRadians(angle);

        LineSegment leftSide = new LineSegment(xWithOffset, pivot.y(), xWithOffset, pivot.y() + lineLength);
        LineSegment rightSide = new LineSegment(xWithOffset +width, pivot.y(), xWithOffset + width, pivot.y() + lineLength);

        leftSide = Geometry.rotateAround(leftSide, pivot, new Angle(radianAngle));
        rightSide = Geometry.rotateAround(rightSide, pivot, new Angle(radianAngle));

        List<LineSegment> lines = new ArrayList<>();
        lines.add(leftSide);
        lines.add(rightSide);

        return lines;
    }

    public List<Circle> getCircles() {
        double lineLength = length - width;
        double radianAngle = Math.toRadians(angle);
        double radius = width/2;


        Circle circleOne = new Circle(pivot, radius);
        Circle circleTwo = new Circle(pivot.plus(new Vect(0, lineLength)), radius);

        circleOne = Geometry.rotateAround(circleOne, pivot, new Angle(radianAngle));
        circleTwo = Geometry.rotateAround(circleTwo, pivot, new Angle(radianAngle));

        List<Circle> circles = new ArrayList<>();
        circles.add(circleOne);
        circles.add(circleTwo);

        return circles;
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
        return pivot;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public boolean isMoving() {
        return moving;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
