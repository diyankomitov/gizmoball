package model.board.gizmos;

import model.BoundingBox;
import model.board.Ball;
import model.board.BoardObjectType;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observer;

import java.util.*;

import static util.Constants.ONE_L;

public class AbsorberGizmo implements Gizmo {

    private double x;
    private double y;

    private double width;

    private double height;
    private final double rCoefficient;

    private final List<LineSegment> sides;
    private final List<Circle> corners;
    private final BoardObjectType type;
    private final String name;

    private final Queue<Ball> balls;

    private final boolean triggered;

    private final List<Observer> observers;
    private double angle;
    private boolean keyPressed;
    private boolean keyReleased;
    private final int shootTimer;
    private int numberOfTriggersLeft;

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
        type = BoardObjectType.ABSORBER;
        shootTimer = 20;
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
    public Vect getCenter() {
        return new Vect((x+width)/2, (y+height)/2);
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
    public void sendTrigger() {
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x,y, x+width, y+height);
    }

    @Override
    public void getTimeUntilCollision() {

    }

    @Override
    public void activateAction() {
        shootBall();
    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public void trigger(boolean keyPressed, boolean keyReleased) {
        if (!this.keyPressed || this.keyReleased || keyPressed || !keyReleased) { //when a key is being held down dont trigger from gizmos
            this.keyReleased = keyReleased;
            this.keyPressed = keyPressed;
        }

        if (this.keyPressed && !this.keyReleased) {
            numberOfTriggersLeft++;
        }

        if (this.keyPressed && this.keyReleased) {
            numberOfTriggersLeft = 0;
        }

        if (!this.keyPressed && this.keyReleased) {
            numberOfTriggersLeft++;
        }
    }

    private void shootBall() {
        if (numberOfTriggersLeft > 0 && balls.size() > 0 && this.getY() > 0) {
            Ball ball = balls.remove();
            ball.setInAbsorber(false);
            ball.setY(this.y - (ball.getDiameter() / 2));
            ball.setVelocity(new Vect(0, -50 * ONE_L));
            numberOfTriggersLeft--;
        }
    }

    @Override
    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
        notifyObservers();
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
