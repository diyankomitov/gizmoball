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
    private double yWithOffset;
    private double offset;
    private final double width;
    private final double length;
    private final double rCoefficient;
    private BoardObjectType type;
    private final String name;
    private double angle;
    private Vect pivot;
    private double angularVelocity;
    private boolean triggered;
//    private double offset;
    private final List<Observer> observers;
    private boolean flipUp;
    private boolean keyPressed;
    private int rotationCount;
    private double startingAngle;
    private double oldAngle;
    private boolean keyReleased;

    /**
     * Constructor
     * @param x
     * @param y
     * @param a
     */
    public FlipperGizmo(double x, double y, double a, BoardObjectType type, String name){
        width = ONE_L/2;
        length = ONE_L*2;

        offset = 0;
        if(type == BoardObjectType.RIGHT_FLIPPER) {
            offset = ONE_L+width;
            this.type = type;
            this.rotationCount = 1;
        }
        else {
            this.type = BoardObjectType.LEFT_FLIPPER;
            rotationCount = 0;
        }

        this.x = x;
        this.xWithOffset = x + offset;
        this.y = y;
        this.yWithOffset = y;

        this.name=name;

        angle = a;
        oldAngle = angle;
        ArrayList<LineSegment> sides = new ArrayList<>();
        ArrayList<Circle> circles = new ArrayList<>();
        rCoefficient = 0.95;

        angularVelocity = Math.toRadians(FLIPPER_ANGULAR_VELOCITY);

        double radius = width/2;
        pivot = new Vect(this.xWithOffset + radius, this.y + radius);
        observers = new ArrayList<>();


    }

    public double getAngle() {
        return angle;
    }

    public double getOldAngle() {
        return oldAngle;
    }

    @Override
    public void activateAction() {
        flip();
    }

    @Override
    public boolean getKeyPressed() {
        return keyPressed;
    }

    @Override
    public void trigger(boolean keyPressed, boolean keyReleased) {
        if (!this.keyPressed || this.keyReleased || keyPressed || !keyReleased) { //when a key is being held down dont trigger from gizmos
            this.keyReleased = keyReleased;
            this.keyPressed = keyPressed;
        }

        if (keyPressed && !keyReleased) {
            flipUp = true;
        }
        else if (!keyPressed && keyReleased) {
            flipUp = true;
        }

    }

    private void flip() {
        oldAngle = angle;
        if(type == BoardObjectType.RIGHT_FLIPPER)
        {
            if (flipUp) {
                angle = Math.min(angle + DELTA_ANGLE, startingAngle + 90);
                angularVelocity = Math.toRadians(FLIPPER_ANGULAR_VELOCITY);
            }
            else {
                angle = Math.max(angle - DELTA_ANGLE, startingAngle);
                angularVelocity = Math.toRadians(-FLIPPER_ANGULAR_VELOCITY);
            }

            if (angle >= 90) {
                if (keyPressed && !keyReleased) {
                    flipUp = true;
                }
                else if(keyPressed) {
                    flipUp = false;
                }
                else if (keyReleased) {
                    flipUp = false;
                }
            }
        }
        else if (type == BoardObjectType.LEFT_FLIPPER)
        {
            if (flipUp) {
                angle = Math.max(angle - DELTA_ANGLE, startingAngle - 90);
                angularVelocity = Math.toRadians(-FLIPPER_ANGULAR_VELOCITY);
            }
            else {
                angle = Math.min(angle + DELTA_ANGLE, startingAngle);
                angularVelocity = Math.toRadians(FLIPPER_ANGULAR_VELOCITY);
            }

            if (angle <= -90) {
                if (keyPressed && !keyReleased) {
                    flipUp = true;
                }
                else if(keyPressed) {
                    flipUp = false;
                }
                else if (keyReleased) {
                    flipUp = false;
                }
            }
        }
        if (angle == 0 || angle == 90 || angle == -90) {
            angularVelocity = 0;
        }


        this.notifyObservers();
    }

    @Override
    public void sendTrigger() {
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x,y, x+length, y+length);
    }

    @Override
    public void getTimeUntilCollision() {

    }

    public void rotate(){
//        angle += 90;
        switch (rotationCount){
            case 0 :
                xWithOffset = x + ONE_L + width;
                type = BoardObjectType.RIGHT_FLIPPER;
                angle = 0;
                startingAngle = 0;
                rotationCount++;
                break;
            case 1:
                yWithOffset = y + ONE_L + width;
                type = BoardObjectType.RIGHT_FLIPPER;
                angle = 90;
                startingAngle = 90;
                rotationCount++;
                break;
            case 2:
                xWithOffset = x;
                type = BoardObjectType.LEFT_FLIPPER;
                angle = -90;
                startingAngle = -90;
                rotationCount++;
                break;
            case 3:
                yWithOffset = y;
                type = BoardObjectType.LEFT_FLIPPER;
                angle = 0;
                startingAngle = 0;
                rotationCount = 0;
                break;
        }

        oldAngle = angle;
        notifyObservers();
    }

    @Override
    public List<LineSegment> getLines() {
        pivot = new Vect(this.xWithOffset + width/2, this.yWithOffset + width/2);
        double lineLength = length - width;
        double radianAngle = Math.toRadians(oldAngle);
        LineSegment leftSide = new LineSegment(xWithOffset, pivot.y(), xWithOffset, pivot.y() + lineLength);
        LineSegment rightSide = new LineSegment(xWithOffset + width, pivot.y(), xWithOffset + width, pivot.y() + lineLength);

        leftSide = Geometry.rotateAround(leftSide, pivot, new Angle(radianAngle));
        rightSide = Geometry.rotateAround(rightSide, pivot, new Angle(radianAngle));

        List<LineSegment> lines = new ArrayList<>();
        lines.add(leftSide);
        lines.add(rightSide);

        return lines;
    }

    public List<Circle> getCircles() {
        pivot = new Vect(this.xWithOffset + width/2, this.yWithOffset + width/2);
        double lineLength = length - width;
        double radianAngle = Math.toRadians(oldAngle);
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
        this.xWithOffset += x-this.x;
        this.yWithOffset += y-this.y;
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

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    public double getxWithOffset() {
        return xWithOffset;
    }

    public double getyWithOffset() {
        return yWithOffset;
    }

    public double getOffset() {
        return offset;
    }

    public Vect getPivot() {
        return pivot;
    }
}
