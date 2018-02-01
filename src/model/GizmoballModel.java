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
//    private List<Ball> balls;

    private Ball ball;
    private double timeUntilCollision;
    private Vect velocity;

    private int sCounter = 0;
    private int tCounter = 0;
    private int cCounter = 0;
    private int bCounter = 0;
    private String id;


    public GizmoballModel(String id) {
        this.id = id;
        gizmos = new ArrayList<>();

    }

    public void addBall(double x, double y, double xv, double yv) {
        ball = new Ball(x, y, xv, yv, 0);
        BoardState.add("Move " + ball.getId() + " " + x + " " + y + " " + xv + " " + yv);
        bCounter++;
//        balls.add(ball);
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
                return false; //TODO: implement proper default
        }

        //add to the string array eg "Type Name CoordX CoordY

        for (Gizmo g:gizmos){
            if((g.getXCoord()==x)&&(g.getYCoord()==y)){
                return false;
            }
        }
        gizmos.add(gizmo);
        BoardState.add(type + " " + id + " " + x + " " + y);
        return true;
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

    public List<Gizmo> getGizmos(){
        return gizmos;
    }

    public Gizmo getGizmoByName(String gName) {
        for(Gizmo g:gizmos) {
            if(g.getName().equals(gName)){
                return g;
            }
        }
        return null;
    }
    public Gizmo getGizmoByCoords(double x, double y) {
        for(Gizmo g:gizmos) {
            if(g.getXCoord() == x){
                if(g.getYCoord() == y){
                    return g;
                }
            }
        }
        return null;
    }

    public Ball getBall() {
        return ball;
    }

//    public Ball getBall(String id) {
//        for (Ball b : balls){
//            if(b.getId().equals(id)){
//                return b;
//            }
//        }
//        return null;
//    }

//    public List<Ball> getBalls(){
//        return balls;
//    }

    public void moveGizmo(int fromX, int fromY, int toX, int toY) {
        BoardState.add("Move " + id + " " + toX + " " + toY);
        //add to that string array for file
        //TODO actually remove gizmo
    }
    public void rotateGizmo(String  id) {
        BoardState.add("Rotate " + id);
        //add to that string array for file
        //TODO actually rotate gizmo
    }

    public void removeGizmo(String id) {
        //add to that string array for file
        BoardState.add("Remove " + id );
        gizmos.remove(getGizmoByName(id));
    }
}
