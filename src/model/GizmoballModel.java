package model;

import physics.*;
import util.BoardState;
import util.Observable;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.FRICTION_MU;
import static util.Constants.FRICTION_MU_2;
import static util.Constants.GRAVITY;

public class GizmoballModel implements Observable{
    private static final double ONE_L_UNIT = 1.0;
    private static final int BOARD_WIDTH = 20;
//    private final LineSegment wall;

    private List<Gizmo> gizmos;

    private Ball ball;
    private double timeUntilCollision;
    private Vect velocity;

    private int sCounter = 0;
    private int tCounter = 0;
    private int cCounter = 0;

    private String id;
    private Gizmo collidedGizmo;

    private Vect gravity = new Vect(0, GRAVITY);

    public GizmoballModel(String id) {
        this.id = id;
        gizmos = new ArrayList<>();
        ball = new Ball(3,0,0,0);

//        wall = new LineSegment(0,0,20,0);
    }

    public void moveBall() {
        double moveTime = 0.05;
        ball.setVelocity(ball.getVelocity().plus(gravity.times(moveTime)));

        double vOldX = ball.getVelocity().x();
        double vOldY = ball.getVelocity().y();

        double vNewX = vOldX * (1-(FRICTION_MU*moveTime) - (FRICTION_MU_2* Math.abs(vOldX) * moveTime));
        double vNewY = vOldY * (1-(FRICTION_MU*moveTime) - (FRICTION_MU_2* Math.abs(vOldY) * moveTime));

        ball.setVelocity(new Vect(vNewX, vNewY));

        findTimeUntilCollision();
        if (!ball.isInAbsorber()) {
            if (timeUntilCollision > moveTime) {
                ball.moveForTime(moveTime);
            }
            else {
                if (collidedGizmo.getType() == GizmoType.ABSORBER) {
                    ((Absorber)collidedGizmo).addBall(ball);
                }
                ball.moveForTime(timeUntilCollision);
                ball.setVelocity(velocity);
            }
        }
//        notifyObservers();
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
                        collidedGizmo = gizmo;
                }
            }

            for (Circle circle : gizmo.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;

                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                    collidedGizmo = gizmo;
                }
            }
        }

//        time = Geometry.timeUntilWallCollision(wall, ball.getCircle(), ball.getVelocity());
//        if (time < timeUntilCollision) {
//            timeUntilCollision = time;
//            velocity = Geometry.reflectWall(wall, ball.getVelocity(), 1);
//        }

    }

    public void addGizmo(Gizmo gizmo) {
        gizmos.add(gizmo);
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

    public List<Gizmo> getGizmos() { return gizmos; }

    public Gizmo getGizmoByName(String gName) {
        for(Gizmo g:gizmos) {
            if(g.getName().equals(gName)){
                return g;
            }
        }
        return null;
    }

    public Ball getBall() {
        return ball;
    }

    public void moveGizmo(int fromX, int fromY, int toX, int toY) {
        BoardState.add("Move " + id + " " + toX + " " + toY);
        //add to that string array for file
    }

    public void removeGizmo(String id) {
        //add to that string array for file
        BoardState.add("Remove " + id );
        gizmos.remove(getGizmoByName(id));
    }
}
