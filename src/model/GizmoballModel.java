package model;

import physics.*;
import util.Observable;

import java.util.ArrayList;
import java.util.List;

public class GizmoballModel implements Observable{
    private static final double ONE_L_UNIT = 1.0;
    private static final int BOARD_WIDTH = 20;

    private List<Gizmo> gizmos;

    private Ball ball;
    private double timeUntilCollision;
    private Vect velocity;

    private int sCounter = 0;
    private int tCounter = 0;
    private int cCounter = 0;


    public GizmoballModel() {
        gizmos = new ArrayList<>();
        ball = new Ball(0,0,1,1);
    }

    public void moveBall() {
        double moveTime = 0.05;
        findTimeUntilCollision();
        if (timeUntilCollision > moveTime) {
            ball.moveForTime(moveTime);
        }
        else {
            ball.moveForTime(timeUntilCollision);
            ball.setVelocity(velocity);
        }
        notifyObservers();
    }


    private void findTimeUntilCollision() {
        timeUntilCollision = Double.MAX_VALUE;
        double time;

        for (Gizmo gizmo : gizmos) {
            for (LineSegment line : gizmo.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectWall(line, ball.getVelocity(), gizmo.getRCoefficient());
                }
            }

            for (Circle circle : gizmo.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                }
            }
        }
    }

    public void addGizmo(double x, double y, GizmoType type) {
        System.out.println("here0");
        Gizmo gizmo;
        System.out.println("here");
        switch (type) {
            case CIRCLE:
                gizmo = new CircleGizmo(x, y, ONE_L_UNIT, cCounter);
                cCounter++;
                System.out.println("here2");
                break;
            case SQUARE:
                gizmo = new SquareGizmo(x,y, ONE_L_UNIT, sCounter);
                sCounter++;
                break;
            case TRIANGLE:
                gizmo = new TriangleGizmo(x,y, ONE_L_UNIT, tCounter);
                tCounter++;
                break;
            default:
                gizmo = null; //TODO: implement proper default
        }

        //add to the string array

        gizmos.add(gizmo);
    }

    public int getCCounter() {
        return cCounter;
    }
    public int getSCounter() {
        return sCounter;
    }
    public int getTCounter() {
        return tCounter;
    }

    public Ball getBall() {
        return ball;
    }

    public void moveGizmo(int fromX, int fromY, int toX, int toY) {

    }

    public void removeGizmo(int x, int y) {

    }
}
