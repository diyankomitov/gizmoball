package sandbox.DiyanS_FX_Version.model;

import sandbox.physics.Circle;
import sandbox.physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;

public class LFlipper{

    private double angle; //Angle the flipper is at
    private double xpos;
    private double ypos;
    private ArrayList<LineSegment> lineSegments; //2 line segments - could render separately?
    private ArrayList<Circle> circles;//2 cirrcles, one larger than the other? same size?
    private Color colour;
    private boolean stopped; //indicates when the flipper is being moved by a keypress

    /**
     * Constructor
     * @param x
     * @param y
     * @param a
     */
    public LFlipper(double x, double y, double a){
        xpos = x;
        ypos = y;
        angle = a;
        //Initialise lines and circles in here too?
    }

    //Getters and setters -self explanatory?

    public double getAngle() {
        return angle;
    }

    public void setAngle(double a){
        angle = a;
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
        stopped = true;
    }

    public void start()
    {
        stopped = false;
    }

    public boolean stopped()
    {
        return stopped;
    }
}
