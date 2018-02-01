package main;

import javafx.scene.paint.Color;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observable;
import util.Observer;

import java.util.ArrayList;

import static main.Constants.DELTA_ANGLE;
import static main.Constants.ONE_L;
import static main.FlipperDirection.LEFT;
import static main.FlipperDirection.RIGHT;

public class Flipper implements Observable{

    private Circle corner2;
    private double angle; //Angle the flipper is at
    private FlipperDirection direction;
    private double xpos;
    private double ypos;
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

        double offset = 0;
        if(direction == FlipperDirection.RIGHT) {
            offset = ONE_L;
        }

        xpos = x + offset;
        ypos = y;

        angle = a;
        this.direction = direction;
        lineSegments = new ArrayList<>();
        circles = new ArrayList<>();
        colour = Color.ORANGE;

        double width = ONE_L/2;
        double length = ONE_L*2;

        LineSegment side1 = new LineSegment(xpos,ypos, xpos,ypos + length);
        LineSegment side2 = new LineSegment(xpos+width, ypos, xpos+width, ypos+length);
        lineSegments.add(side1);
        lineSegments.add(side2);

        Circle corner1 = new Circle(xpos+width/2, y, width/2);
        corner2 = new Circle(xpos+width/2, y+length, width/2);
        circles.add(corner1);
        circles.add(corner2);
    }

    //Getters and setters -self explanatory?

    public double getAngle() {
        return angle;
    }


    public void rotate (){
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

        lineSegments.forEach(lineSegment -> {
            double x1 = lineSegment.p1().x();
            double y1 = lineSegment.p1().y();
            double x2 = lineSegment.p2().x();
            double y2 = lineSegment.p2().y();
            double newX2 = x2*Math.cos(angle) - y2*Math.sin(angle);
            double newY2 = y2*Math.cos(angle) + x2*Math.sin(angle);
            lineSegment = new LineSegment(x1, y1, newX2, newY2);
        });

        double cX = corner2.getCenter().x();
        double cY = corner2.getCenter().y();
        double newCX = cX*Math.cos(angle) - cY*Math.sin(angle);
        double newCY = cY*Math.cos(angle) + cX*Math.sin(angle);
        corner2 = new Circle(newCX,newCY,corner2.getRadius());

        this.notifyObservers();
    }

    public ArrayList<Circle> getCircles() {
        //Perhaps returns a new array list of circles of a paticular size
        return circles;
    }

    public ArrayList<LineSegment> getLineSegments() {
        //like the get circles method
        return lineSegments;
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
