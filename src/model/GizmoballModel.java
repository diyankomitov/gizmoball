package model;

import model.board.Ball;
import model.board.Board;
import model.board.BoardObjectType;
import model.board.Walls;
import model.board.gizmos.*;
import physics.*;
import util.BoardState;
import view.FlipperDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Constants.*;

public class GizmoballModel{
    private static final double ONE_L_UNIT = 1.0;
    private CollisionDetails details;
    private Board board;
    private Vect gravity;
    private Vect friction;

    private BoardObjectType collidedGizmo;


    public GizmoballModel() {
        board = new Board();
        details = new CollisionDetails();
        gravity = new Vect(0, GRAVITY);
    }

    public void moveBalls() {
        double moveTime = SECONDS_PER_FRAME;
        for (Ball ball : board.getBalls()) {

            ball.setVelocity(ball.getVelocity().plus(gravity.times(moveTime)));

            double vOldX = ball.getVelocity().x();
            double vOldY = ball.getVelocity().y();

            double vNewX = vOldX * (1 - (FRICTION_MU * moveTime) - (FRICTION_MU_2 * Math.abs(vOldX) * moveTime));
            double vNewY = vOldY * (1 - (FRICTION_MU * moveTime) - (FRICTION_MU_2 * Math.abs(vOldY) * moveTime));

            ball.setVelocity(new Vect(vNewX, vNewY));
        }

            findTimeUntilCollision();
//        System.out.println(ball.getVelocity());
        board.getBalls().forEach(ball -> {
            if (!ball.isInAbsorber()) {
                if (details.getTimeUntilCollission(ball) > moveTime) {
                    ball.moveForTime(moveTime);
                }
                else {
                    if (collidedGizmo != null) {
                        if (collidedGizmo == BoardObjectType.ABSORBER) {
                            ((AbsorberGizmo)getGizmoByName("A")).addBall(ball);
                        }
                    }
                    ball.moveForTime(details.getTimeUntilCollission(ball));
                    ball.applyPotentialVelocity();
                }
            }
        });

    }




    private void findTimeUntilGizmoCollission(Gizmo gizmo, Ball ball) {
        double time;
        List<LineSegment> lines = gizmo.getLines();
        List<Circle> circles = gizmo.getCircles();
        Vect velocity;
        double timeUntilCollision = details.getTimeUntilCollission(ball);


        if (gizmo.isRotating()) {

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), gizmo.getAngularVelocity(), ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), gizmo.getAngularVelocity(), ball.getCircle(), ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), gizmo.getAngularVelocity(), ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), gizmo.getAngularVelocity(), ball.getCircle(), ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }
        }
        else {
            for (LineSegment line : lines) {
                time = Geometry.timeUntilWallCollision(line, ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectWall(line, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }
        }

        details.setTimeUntilCollission(ball, timeUntilCollision);

    }

    private void findTimeUntilWallsCollission(Walls walls, Ball ball) {
        double time;
        List<LineSegment> lines = walls.getLines();
        List<Circle> circles = walls.getCircles();
        Vect velocity;
        double timeUntilCollision = details.getTimeUntilCollission(ball);

        for (LineSegment line : lines) {
            time = Geometry.timeUntilWallCollision(line, ball.getCircle(), ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectWall(line, ball.getVelocity(), walls.getRCoefficient());
                ball.setPotentialVelocity(velocity);
            }
        }

        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ball.getCircle(), ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), walls.getRCoefficient());
                ball.setPotentialVelocity(velocity);
            }
        }

        details.setTimeUntilCollission(ball, timeUntilCollision);
    }

    private void findTimeUntilBallCollision(Ball otherBall, Ball ball) {
        double timeUntilCollision = details.getTimeUntilCollission(ball);
        double timeUntilCollisionOther = details.getTimeUntilCollission(otherBall);
        double time;

        time = Geometry.timeUntilBallBallCollision(ball.getCircle(), ball.getVelocity(), otherBall.getCircle(), otherBall.getVelocity());
        Geometry.VectPair velocities = Geometry.reflectBalls(ball.getCenter(), 1, ball.getVelocity(), otherBall.getCenter(), 1, otherBall.getVelocity());

        if (time < timeUntilCollision) {
            timeUntilCollision = time;
            ball.setPotentialVelocity(velocities.v1);
            System.out.println(ball.getName() + ": " + velocities.v1);
        }

        if (time < timeUntilCollisionOther) {
            timeUntilCollisionOther = time;
            otherBall.setPotentialVelocity(velocities.v2);
            System.out.println(otherBall.getName() + ": " + velocities.v2);

        }
        details.setTimeUntilCollission(ball, timeUntilCollision);
        details.setTimeUntilCollission(otherBall, timeUntilCollisionOther);
    }

    private void findTimeUntilCollision() {

        Map<Ball, List<Ball>> checked = new HashMap<>();

        board.getBalls().forEach(ball -> {
            details.setTimeUntilCollission(ball, Double.MAX_VALUE);
        });

        board.getBalls().forEach(ball -> {
            board.getGizmos().forEach(gizmo -> findTimeUntilGizmoCollission(gizmo, ball));

            findTimeUntilWallsCollission(board.getWalls(), ball);

            checked.put(ball, new ArrayList<>());
            checked.get(ball).add(ball);

            board.getBalls().forEach(otherBall -> {
                checked.putIfAbsent(otherBall, new ArrayList<>());
                checked.get(otherBall).add(otherBall);
                if (!checked.get(ball).contains(otherBall) && !checked.get(otherBall).contains(ball)) {
                    findTimeUntilBallCollision(otherBall, ball);
                }

                checked.get(ball).add(otherBall);
                checked.get(otherBall).add(ball);
            });
        });
    }

//    public void addGizmo(Gizmo gizmo) {
//        board.add(gizmo);
//    }

    public boolean addGizmo(double x, double y, String name, BoardObjectType type) {
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
                    gizmo = new FlipperGzmo(x, y, 0, FlipperDirection.LEFT, "LF"+(int)x + (int)y);

                } else {
                    gizmo = new FlipperGzmo(x ,y, 0, FlipperDirection.LEFT, name);
                }
                break;
            case RIGHT_FLIPPER:
                if(name.equals("")) {
                    gizmo = new FlipperGzmo(x, y, 0, FlipperDirection.RIGHT, "RF"+(int)x + (int)y);
                } else {
                    gizmo = new FlipperGzmo(x ,y, 0, FlipperDirection.RIGHT, name);
                }
                break;
            default:
                return false; //TODO: implement proper default
        }


        for (Gizmo g:board.getGizmos()){
            if((g.getXCoord()==x)&&(g.getYCoord()==y)){
                return false;
            }
        }
        board.add(gizmo);
        BoardState.add(type + " " + name + " " + x + " " + y);
        return true;
    }

    public boolean addAbsorber(double x, double y, double x2, double y2,String name){

        return board.add(new AbsorberGizmo(x,y,x2,y2, name));

    }

    public void clearGizmos() {
        board.clear();
    }

    public List<Gizmo> getGizmos(){
        return board.getGizmos();
    }

    public Gizmo getGizmoByName(String gName) {
        for(Gizmo g:board.getGizmos()) {
            if(g.getName().equals(gName)){
                return g;
            }
        }
        return null;
    }

    public Gizmo getGizmoByCoords(double x, double y) {
        for(Gizmo g:board.getGizmos()) {
            if(g.getXCoord() == x){
                if(g.getYCoord() == y){
                    return g;
                }
            }
        }
        return null;
    }

    public List<Ball> getBalls() {
//        return ball;
        return board.getBalls();
    }

    public void addBall(double x, double y, double xv, double yv, String name) {
        Ball ball = new Ball(x, y, xv, yv, name);
        board.add(ball);
        details.addBall(ball);
        BoardState.add("Move " + name + " " + x + " " + y + " " + xv + " " + yv);
    }

    public void removeBall() {
//        board.remove();
    }

    //        for (Ball b : balls){
//    public Ball getBall(String id) {
//            if(b.getId().equals(id)){
//                return b;
//            }
//        }
//        return null;
//    }

//    public List<Ball> getBalls(){
//        return balls;
//    }

//    public void moveGizmo(int fromX, int fromY, int toX, int toY) {
//        BoardState.add("Move " + id + " " + toX + " " + toY);
//        //add to that string array for file
//        //TODO actually remove gizmo
//    }

    public void rotateGizmo(String  id) {
        BoardState.add("Rotate " + id);
        //add to that string array for file
        //TODO actually rotate gizmo
        Gizmo target = getGizmoByName(id);
        if(target.getType() == BoardObjectType.TRIANGLE){
            //Rotate
            ((TriangleGizmo)target).rotate();

        }
    }

    public void removeGizmo(String id) {
        //add to that string array for file
        BoardState.add("Delete " + id );
        board.remove(getGizmoByName(id));
    }

}
