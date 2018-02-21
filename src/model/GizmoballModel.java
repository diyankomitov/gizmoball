package model;

import physics.*;
import util.BoardState;
import util.Observable;
import view.FlipperDirection;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.*;

public class GizmoballModel implements Observable{
    private static final double ONE_L_UNIT = 1.0;
    private static final int BOARD_WIDTH = 20;
//    private final LineSegment wall;

    private List<Gizmo> gizmos;
//    private List<Ball> balls;

    private Ball ball;
    private double timeUntilCollision;
    private Vect velocity;

    private Gizmo collidedGizmo;
    private List<LineSegment> walls;
    private List<Circle> wallCircles;

    private Vect gravity = new Vect(0, GRAVITY);

    public GizmoballModel() {

        gizmos = new ArrayList<>();


        walls = new ArrayList<>();
        walls.add(new LineSegment(0,0,0,20));
        walls.add(new LineSegment(0,20,20,20));
        walls.add(new LineSegment(20,20,20,0));
        walls.add(new LineSegment(20,0,0,0));

        wallCircles = new ArrayList<>();
        wallCircles.add(new Circle(0,0,0));
        wallCircles.add(new Circle(20,0,0));
        wallCircles.add(new Circle(20,20,0));
        wallCircles.add(new Circle(0,20,0));

    }

    public void addBall(double x, double y, double xv, double yv, String name) {
        ball = new Ball(x, y, xv, yv, name);
        BoardState.add("Ball " + name + " " + x + " " + y + " " + xv + " " + yv + "\n");
//        balls.add(ball);
    }

    public void removeBall() {
        ball = null;
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
        System.out.println(ball.getVelocity());
        if (!ball.isInAbsorber()) {
            if (timeUntilCollision > moveTime) {
                ball.moveForTime(moveTime);
            }
            else {
                if (collidedGizmo != null) {
                    if (collidedGizmo.getType() == GizmoType.ABSORBER) {
                        ((Absorber)collidedGizmo).addBall(ball);
                    }
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

            if (gizmo.getType() == GizmoType.LEFT_FLIPPER || gizmo.getType() == GizmoType.RIGHT_FLIPPER) {
                if (((Flipper)gizmo).isMoving()) {
                    System.out.println("trying");
                    for (LineSegment line : gizmo.getLines()) {
                        time = Geometry.timeUntilRotatingWallCollision(line,((Flipper) gizmo).getCenter(), Math.toRadians(FLIPPER_ANGULAR_VELOCITY), ball.getCircle(), ball.getVelocity());
                        if (time < timeUntilCollision) {
                            timeUntilCollision = time;
                            velocity = Geometry.reflectRotatingWall(line, ((Flipper) gizmo).getCenter(), Math.toRadians(FLIPPER_ANGULAR_VELOCITY), ball.getCircle(), ball.getVelocity(), gizmo.getRCoefficient());
                            collidedGizmo = gizmo;
                        }
                    }
                    for (Circle circle : gizmo.getCircles()) {
                        time = Geometry.timeUntilRotatingCircleCollision(circle, ((Flipper) gizmo).getCenter(), Math.toRadians(FLIPPER_ANGULAR_VELOCITY), ball.getCircle(), ball.getVelocity());
                        if (time < timeUntilCollision) {
                            timeUntilCollision = time;

                            velocity = Geometry.reflectRotatingCircle(circle, ((Flipper) gizmo).getCenter(), Math.toRadians(FLIPPER_ANGULAR_VELOCITY), ball.getCircle(), ball.getVelocity(), gizmo.getRCoefficient());
                            collidedGizmo = gizmo;
                        }
                    }
                }
            }
        }

        for (LineSegment line : walls) {
            time = Geometry.timeUntilWallCollision(line, ball.getCircle(), ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectWall(line, ball.getVelocity(), 1);
                collidedGizmo = null;
            }
        }
        for (Circle corner : wallCircles) {
            time = Geometry.timeUntilCircleCollision(corner, ball.getCircle(), ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectCircle(corner.getCenter(), ball.getCenter(), ball.getVelocity(), 1);
                collidedGizmo = null;
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

    public boolean addGizmo(double x, double y, GizmoType type, String name) {
        Gizmo gizmo;

        switch (type) {
            case CIRCLE:
                if(name.equals("")) {
                    gizmo = new CircleGizmo(x, y, ONE_L_UNIT, "C" + (int)x + (int)y);
                }else{
                    gizmo = new CircleGizmo(x, y, ONE_L_UNIT, name);
                }
                break;
            case SQUARE:
                if(name.equals("")) {
                    gizmo = new SquareGizmo(x,y, ONE_L_UNIT,"S" + (int)x + (int)y );
                }else{
                    gizmo = new SquareGizmo(x,y, ONE_L_UNIT,name );
                }
                break;
            case TRIANGLE:
                if(name.equals("")) {
                    gizmo = new TriangleGizmo(x, y, ONE_L_UNIT, "T" + (int)x + (int)y);
                } else {
                    gizmo = new TriangleGizmo(x, y, ONE_L_UNIT, name);

                }
                break;
            case LEFT_FLIPPER:
                if(name.equals("")) {
                    gizmo = new Flipper(x, y, 0, FlipperDirection.LEFT, "LF"+(int)x + (int)y);

                } else {
                    gizmo = new Flipper(x ,y, 0, FlipperDirection.LEFT, name);
                }
                break;
            case RIGHT_FLIPPER:
                if(name.equals("")) {
                    gizmo = new Flipper(x, y, 0, FlipperDirection.RIGHT, "RF"+(int)x + (int)y);
                } else {
                    gizmo = new Flipper(x ,y, 0, FlipperDirection.RIGHT, name);
                }
                break;
            default:
                return false; //TODO: implement proper default
        }


        for (Gizmo g:gizmos){
            if((g.getXCoord()==x)&&(g.getYCoord()==y)){
                return false;
            }
        }
        gizmos.add(gizmo);
        BoardState.add(type.toString() + " " + name + " " + (int)x + " " + (int)y + "\n");
        return true;
    }

    public boolean addAbsorber(double x, double y, double x2, double y2,String name){
        BoardState.add("Absorber" + " " + name + " " + (int)x + " " + (int)y+ " " + (int)x2 + " "+ (int)y2 +"\n");
        return gizmos.add(new Absorber(x,y,x2,y2, name));

    }

    public void clearGizmos() {
        gizmos = new ArrayList<>();
        ball=null;
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

    public void moveGizmo(String name, int toX, int toY) {
        BoardState.add("Move " + name + " " + toX + " " + toY + "\n");
        //add to that string array for file
        //TODO actually remove gizmo
    }

    public void rotateGizmo(String  name) {
        BoardState.add("Rotate " + name + "\n");
        //add to that string array for file
        //TODO actually rotate gizmo
        Gizmo target = getGizmoByName(name);
        if(target.getType() == GizmoType.TRIANGLE){
            //Rotate
            ((TriangleGizmo)target).rotate();

        }
    }

    public void removeGizmo(String name) {
        //add to that string array for file
        BoardState.add("Delete " + name + "\n" );
        gizmos.remove(getGizmoByName(name));
    }

}
