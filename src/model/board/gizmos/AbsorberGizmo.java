package model.board.gizmos;

import model.board.Ball;
import model.board.BoardObjectType;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static util.Constants.ONE_L;

public class AbsorberGizmo implements Gizmo {

    private double x;
    private double y;

    private double width;

    private double height;
    private double rCoefficient;

    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private BoardObjectType type = BoardObjectType.ABSORBER;
    private String name;

    private Queue<Ball> balls;

    private boolean triggered;

    private List<Observer> observers;

    public AbsorberGizmo(double x, double y, double x2, double y2, String name) {

        this.x = x;
        this.y = y;

        this.name = name;

        this.width = x2 - x;
        this.height = y2 - y;

        rCoefficient = Double.NEGATIVE_INFINITY;

        sides = new ArrayList<>();
        corners = new ArrayList<>();
        observers = new ArrayList<>();

        balls = new LinkedList<>();

        triggered = false;
    }

    public void shootBall() {
        if (balls.size() > 0 ) {
            Ball ball = balls.remove();
            ball.setInAbsorber(false);
            ball.setY(this.y-(ball.getDiameter()/2));
            ball.setVelocity(new Vect(0, -50*ONE_L));
        }
    }

    @Override
    public List<LineSegment> getLines() {
        sides.clear();

        LineSegment ls1 = new LineSegment(x,y,x+width, y);
        LineSegment ls2 = new LineSegment(x+width,y,x+width, y+height);
        LineSegment ls3 = new LineSegment(x+width,y+height,x, y+height);
        LineSegment ls4 = new LineSegment(x,y+height,x, y);
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
        Circle c3 = new Circle(x+width, y+height, 0);
        Circle c4 = new Circle(x, y+height, 0);
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
    public void rotate() {
        //You cannot rotate an absorber
        //TODO: return some dummy value?
    }

    @Override
    public double getAngle() {
        return 0;
    }

    @Override
    public void trigger() {
        triggered = true;
    }

    @Override
    public void sendTrigger() {
        //TODO: implement this method when we implement the trigger system
    }

    @Override
    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public BoardObjectType getType() {
        return type;
    }

    public void addBall(Ball ball) {
        ball.setVelocity(new Vect(0,0));
        ball.setX(x + width - 0.25*ONE_L);
        ball.setY(y + height - 0.25*ONE_L);
        ball.setInAbsorber(true);
        this.balls.add(ball);
    }

    public Queue<Ball> getBalls() {
        return balls;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isTriggered()
    {
        return triggered;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}