package model;

import javafx.scene.paint.Color;
import view.FlipperDirection;
import physics.Circle;
import physics.LineSegment;
import util.Observable;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.DELTA_ANGLE;
import static util.Constants.ONE_L;
import static view.FlipperDirection.LEFT;
import static view.FlipperDirection.RIGHT;

public class Flipper implements Gizmo, Observable{

    private double xpos;
    private double ypos;
    private final double width;
    private final double length;
    private Circle corner2;
    private double angle; //Angle the flipper is at
    private FlipperDirection direction;
    private ArrayList<LineSegment> lineSegments; //2 line segments - could render separately?
    private ArrayList<Circle> circles;//2 cirrcles, one larger than the other? same size?
    private Color colour;
    private boolean triggered; //indicates when the flipper is being moved by a keypress

    /**
     * Constructor
     * @param x
     * @param y
     * @param a
     */
    public Flipper(double x, double y, double a, FlipperDirection direction){
        width = ONE_L/2;
        length = ONE_L*2;

        double offset = 0;
        if(direction == FlipperDirection.RIGHT) {
            offset = ONE_L+width;
        }

        xpos = x + offset;
        ypos = y;

        angle = a;
        this.direction = direction;
        lineSegments = new ArrayList<>();
        circles = new ArrayList<>();
        colour = Color.ORANGE;




//        Circle corner1 = new Circle(xpos+width/2, y, width/2);
//        corner2 = new Circle(xpos+width/2, y+length, width/2);
//        circles.add(corner1);
//        circles.add(corner2);
    }

    //Getters and setters -self explanatory?

    public double getAngle() {
        return angle;
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

        }
        else if (direction == LEFT)
        {
            if (triggered) {
                angle = Math.max(angle - DELTA_ANGLE, -90);
            }
            else {
                angle = Math.min(angle + DELTA_ANGLE, 0);
            }
        }

//        lineSegments.forEach(lineSegment -> {
//            double x1 = lineSegment.p1().x();
//            double y1 = lineSegment.p1().y();
//            double x2 = lineSegment.p2().x();
//            double y2 = lineSegment.p2().y();
//            double newX2 = x2*Math.cos(angle) - y2*Math.sin(angle);
//            double newY2 = y2*Math.cos(angle) + x2*Math.sin(angle);
//            lineSegment = new LineSegment(x1, y1, newX2, newY2);
//        });

//        double cX = corner2.getCenter().x();
//        double cY = corner2.getCenter().y();
//        double newCX = cX*Math.cos(angle) - cY*Math.sin(angle);
//        double newCY = cY*Math.cos(angle) + cX*Math.sin(angle);
//        corner2 = new Circle(newCX,newCY,corner2.getRadius());

        this.notifyObservers();
    }

    @Override
    public List<LineSegment> getLines() {
        double lineLength = length - width;
        double radianAngle = Math.toRadians(angle);

        double otherAngle = Math.toRadians(angle+90);

        double radius = width/2;

        double centerX = xpos + radius;
        double centerY = ypos + radius;
        double lineXOffset = lineLength * Math.cos(otherAngle);
        double lineYOffset = lineLength * Math.sin(otherAngle);

//        System.out.println("angle: " + angle + " radian: " + radianAngle + " cos: " + Math.cos(radianAngle) + " offset: " + lineXOffset);

        double lineY = ypos + width/2;




//        double x1 = radius * (Math.cos(radianAngle) * -1) + radius + xpos;
//        double y1 = radius * (Math.sin(radianAngle) * -1) + radius + ypos;
//
//        double x2 = (Math.cos(radianAngle) * (width - radius)) + radius + xpos;
//        double y2 = (Math.sin(radianAngle) * (width - radius)) + radius + xpos;
//        double y2 = radius * (-Math.sin(radianAngle)) + radius + xpos + width;


        double x1 = centerX + ((xpos - centerX) * Math.cos(radianAngle)) - ((lineY-centerY)*Math.sin(radianAngle));
        double y1 = centerY + ((xpos - centerX) * Math.sin(radianAngle)) - ((lineY-centerY)*Math.cos(radianAngle));

        double x2 = centerX + ((xpos+width) - centerX) * Math.cos(radianAngle) - (lineY-centerY)*Math.sin(radianAngle);
        double y2 = centerY + ((xpos+width) - centerX) * Math.sin(radianAngle) - (lineY-centerY)*Math.cos(radianAngle);



        System.out.println("x1: " + (x1+lineXOffset) + " y1: " + (y1+lineYOffset) + " x2: " + (x2+lineXOffset) + " y2: " + (y2+lineYOffset));


//
//        double x1 = (width/2) * Math.cos(radianAngle) - width/2;
//        double y1 = (width/2) * Math.sin(radianAngle) + width/2;
//
//        double x2 = (width/2) * Math.cos(radianAngle) + width/2;
//        double y2 = (width/2) * Math.sin(radianAngle) + width/2;

        LineSegment side1 = new LineSegment(x1, y1, x1 + lineXOffset,y1 + lineYOffset);
        LineSegment side2 = new LineSegment(x2, y2, x2 + lineXOffset, y2 + lineYOffset);


//        System.out.println("x: " + x1);
//        System.out.println("sidex: " + side1.p1().x());

        ArrayList<LineSegment> list = new ArrayList<>();
        list.add(side1);
        list.add(side2);

        return list;
    }

    public ArrayList<Circle> getCircles() {
        //Perhaps returns a new array list of circles of a paticular size
        return new ArrayList<>();
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
        return "blah";
    }

    @Override
    public GizmoType getType() {
        return GizmoType.LEFT_FLIPPER;
    }

    @Override
    public double getXCoord() {
        return xpos;
    }

    @Override
    public double getYCoord() {
        return ypos;
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
}
