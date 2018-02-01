package model;

import physics.*;
import util.BoardState;
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

    private String id;


    public GizmoballModel(String id) {
        this.id = id;
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

    public boolean addGizmo(double x, double y, GizmoType type) {

        Gizmo gizmo;
            switch (type) {
            case CIRCLE:
                gizmo = new CircleGizmo(x, y, ONE_L_UNIT, cCounter);
                id = gizmo.getName();
                cCounter++;
                break;
            case SQUARE:
                gizmo = new SquareGizmo(x,y, ONE_L_UNIT, sCounter);
                id = gizmo.getName();
                sCounter++;

                break;
            case TRIANGLE:
                gizmo = new TriangleGizmo(x,y, ONE_L_UNIT, tCounter);
                id = gizmo.getName();
                tCounter++;
                break;
            default:
                gizmo = null; //TODO: implement proper default
        }

        //add to the string array eg "Type Name CoordX CoordY

        if((!gizmos.contains(x))&& (!gizmos.contains(y))){
            gizmos.add(gizmo);
            BoardState.add(type + " " + id + " " + x + " " + y);

            return true;
        } else {
            return false;
        }

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
        BoardState.add("Move " + id + " " + toX + " " + toY);
        //add to that string array for file
    }

    public void removeGizmo(int x, int y) {
        //add to that string array for file
    }
}
