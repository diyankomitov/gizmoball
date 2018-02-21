package model.board.gizmos;

import javafx.scene.paint.Color;
import model.board.BoardObjectType;
import physics.*;
import util.Observer;
import view.FlipperDirection;
import util.Observable;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.DELTA_ANGLE;
import static util.Constants.FLIPPER_ANGULAR_VELOCITY;
import static util.Constants.ONE_L;
import static view.FlipperDirection.LEFT;
import static view.FlipperDirection.RIGHT;

public class FlipperGzmo implements Gizmo{

    private double xpos;
    private double ypos;
    private final double width;
    private final double length;
    private double angle; //Angle the flipper is at
    private FlipperDirection direction;
    private ArrayList<LineSegment> lineSegments; //2 line segments - could render separately?
    private ArrayList<Circle> circles;//2 cirrcles, one larger than the other? same size?
    private Color colour;
    private boolean triggered; //indicates when the flipper is being moved by a keypress
    private boolean moving;
    private String name;
    private double offset;

    private Vect center;
    private boolean rotating;
    private double angularVelocity;
    private List<Observer> observers;

    /**
     * Constructor
     * @param x
     * @param y
     * @param a
     */
    public FlipperGzmo(double x, double y, double a, FlipperDirection direction, String name){
        width = ONE_L/2;
        length = ONE_L*2;

        offset = 0;
        if(direction == FlipperDirection.RIGHT) {
            offset = ONE_L+width;
        }

        xpos = x + offset;
        ypos = y;

        this.name=name;

        angle = a;
        this.direction = direction;
        lineSegments = new ArrayList<>();
        circles = new ArrayList<>();
        colour = Color.ORANGE;

        double radius = width/2;
        center = new Vect(xpos + radius, ypos + radius);
        observers = new ArrayList<>();


//        Circle corner1 = new Circle(xpos+width/2, y, width/2);
//        corner2 = new Circle(xpos+width/2, y+length, width/2);
//        circles.addGizmo(corner1);
//        circles.addGizmo(corner2);
    }

    //Getters and setters -self explanatory?

    public double getAngle() {
        return angle;
    }

    public double getOffset() {
        return offset;
    }
    public void rotate(){
        if(direction == RIGHT)
        {
            if (triggered) {
                angle = Math.min(angle + DELTA_ANGLE, 90);
            }
            else {
                angle = Math.max(angle - DELTA_ANGLE, 0);
            }

            if (angle > 0 && angle < 90) {
                moving = true;
            }
            else {
                moving = false;
            }

        }
        else if (direction == LEFT)
        {
            if (triggered) {
                angle = Math.max(angle - DELTA_ANGLE, -90);
            }
            else {
                angle = Math.min(angle + DELTA_ANGLE, 0);
            }

            if (angle < 0 && angle > -90) {
                moving = true;
            }
            else {
                moving = false;
            }
        }

        this.notifyObservers();
    }

    @Override
    public List<LineSegment> getLines() {
        double lineLength = length - width;
        double radianAngle = Math.toRadians(angle);

        LineSegment leftSide = new LineSegment(xpos,center.y(), xpos, center.y() + lineLength);
        LineSegment rightSide = new LineSegment(xpos+width, center.y(), xpos + width, center.y() + lineLength);

        leftSide = Geometry.rotateAround(leftSide, center, new Angle(radianAngle));
        rightSide = Geometry.rotateAround(rightSide, center, new Angle(radianAngle));

        List<LineSegment> lines = new ArrayList<>();
        lines.add(leftSide);
        lines.add(rightSide);

        return lines;
    }

    public List<Circle> getCircles() {
        double lineLength = length - width;
        double radianAngle = Math.toRadians(angle);
        double radius = width/2;


        Circle circleOne = new Circle(center, radius);
        Circle circleTwo = new Circle(center.plus(new Vect(0, lineLength)), radius);

        circleOne = Geometry.rotateAround(circleOne, center, new Angle(radianAngle));
        circleTwo = Geometry.rotateAround(circleTwo, center, new Angle(radianAngle));

        List<Circle> circles = new ArrayList<>();
        circles.add(circleOne);
        circles.add(circleTwo);

        return circles;
    }

    @Override
    public double getRCoefficient() {
        return 0.95;
    }

    @Override
    public void setCoords(double x, double y) {
        this.xpos = x;
        this.ypos = y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BoardObjectType getType() {
        switch (direction) {
            case RIGHT:
                return BoardObjectType.RIGHT_FLIPPER;
            case LEFT:
                return BoardObjectType.LEFT_FLIPPER;
                default: return null;
        }
    }

    @Override
    public double getXCoord() {
        return xpos;
    }

    @Override
    public double getYCoord() {
        return ypos;
    }

    @Override
    public boolean isRotating() {
        return rotating;
    }

    @Override
    public Vect getCenter() {
        return center;
    }

    @Override
    public double getAngularVelocity() {
        return Math.toRadians(FLIPPER_ANGULAR_VELOCITY);
    }

    public double getXpos() {
        return xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public Color getColour() {
        return colour;
    }

    //End of Getters and Setters

    //Methods related to stopping and starting the rotation of the flipper
    //inspired by the stop/start methods from ball - since both move

    public void stop()
    {
        triggered = true;
    }

    public void start()
    {
        triggered = false;
    }

    public boolean stopped()
    {
        return triggered;
    }

    public FlipperDirection getDirection() {
        return direction;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public boolean isMoving() {
        return moving;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
