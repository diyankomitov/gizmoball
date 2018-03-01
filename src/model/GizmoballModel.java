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

import static model.board.BoardObjectType.*;
import static util.Constants.*;

public class GizmoballModel{
    private CollisionDetails details;
    private Board board;
    private Vect gravity;
    private double frictionMU;
    private double frictionMU2;

    private BoardObjectType collidedGizmo;


    public GizmoballModel() {
        board = new Board();
        details = new CollisionDetails();
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;
    }

    public void moveBalls() {
        double moveTime = SECONDS_PER_FRAME;
        for (Ball ball : board.getBalls()) {

            ball.setVelocity(ball.getVelocity().plus(gravity.times(moveTime)));

            double vOldX = ball.getVelocity().x();
            double vOldY = ball.getVelocity().y();

            double vNewX = vOldX * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldX) * moveTime));
            double vNewY = vOldY * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldY) * moveTime));

            ball.setVelocity(new Vect(vNewX, vNewY));
        }

        findTimeUntilCollision();

        board.getBalls().forEach(ball -> {
            if (!ball.isInAbsorber()) {
                if (details.getTimeUntilCollission(ball) > moveTime) {
                    ball.moveForTime(moveTime);
                }
                else {
                    if (collidedGizmo != null) {
                        if (collidedGizmo == ABSORBER) {
                            ((AbsorberGizmo) getGizmo("A")).addBall(ball);
                        }
                    }
                    ball.moveForTime(details.getTimeUntilCollission(ball)); //TODO: Fix issue where ball velocity becomes too low and stops abruptly
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
        Circle ballCircle = ball.getCircles().get(0);

        if ((gizmo.getType() == LEFT_FLIPPER || gizmo.getType() == RIGHT_FLIPPER) && ((FlipperGizmo)gizmo).isMoving()) {
            //TODO: hopefully remove the casting, probably through interface for moving gizmos or maybe reduce it to one cast at the start

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity()); //TODO: Fix flipper collision
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }
        }
        else {
            for (LineSegment line : lines) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectWall(line, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo.getType();
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
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
        Circle ballCircle = ball.getCircles().get(0);

        for (LineSegment line : lines) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectWall(line, ball.getVelocity(), walls.getRCoefficient());
                ball.setPotentialVelocity(velocity);
            }
        }

        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
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

        time = Geometry.timeUntilBallBallCollision(ball.getCircles().get(0), ball.getVelocity(), otherBall.getCircles().get(0), otherBall.getVelocity());
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

    //TODO: Maybe move position checking of add and move to the board, or at least a private method
    public boolean addGizmo(double x, double y, String name, BoardObjectType type) {
        Gizmo gizmo;
        String gizmoName = name; //TODO: check if gizmo with same name already exists

        switch (type) {
            case CIRCLE:
                if(name.equals("")) {
                    gizmoName = "C" + (int)x + (int)y;
                }

                gizmo = new CircleGizmo(x, y, ONE_L, gizmoName);
                break;
            case SQUARE:
                if(name.equals("")) {
                    gizmoName = "S" + (int)x + (int)y;
                }

                gizmo = new SquareGizmo(x, y, ONE_L, gizmoName);
                break;
            case TRIANGLE:
                if(name.equals("")) {
                    gizmoName = "T" + (int)x + (int)y;
                }

                gizmo = new TriangleGizmo(x, y, ONE_L, gizmoName);
                break;
            case LEFT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = "LF" + (int)x + (int)y;
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            case RIGHT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = "RF" + (int)x + (int)y;
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            default:
                return false;
        }


        for (Gizmo g : board.getGizmos()){
            if (gizmo.getBoundingBox().isIntersecting(g.getBoundingBox())){
                return false;
            }
        }

        board.addGizmo(gizmo);

        BoardState.add(type + " " + name + " " + x + " " + y);

        return true;
    }

    public boolean addAbsorber(double x, double y, double x2, double y2, String name){
        if(name.equals("")) {
            name = "A" + (int)x + (int)y;
        }
        if (board.getGizmos().isEmpty()){
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            board.addGizmo(absorber);
            //TODO: should we add a details.addGizmo(?) here?
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y);
            return true;
        }
        else{
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            for (Gizmo gizmo : board.getGizmos()) {
                if (absorber.getBoundingBox().isIntersecting(gizmo.getBoundingBox())){
                    return false;
                }
                if (gizmo.getName().equals(name)){
                    return false;
                }
            }
            board.addGizmo(absorber);
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y);
            return true;
        }
    }


    public boolean addBall(double x, double y, double xv, double yv, String name) {
        if (board.getBalls().isEmpty()){
            Ball ball = new Ball(x, y, xv, yv, name);
            board.addBall(ball);
            details.addBall(ball);
            BoardState.add("Add " + name + " " + x + " " + y + " " + xv + " " + yv);
            return true;
        }
        else {
            for (Ball b : board.getBalls()) {
                //if name of ball being added is NOT equal to the name of some other ball
                if (!b.getName().equals(name)) {
                    if (b.getX() == x && b.getY() == y) {
                        return false;
                    }
                    for (Gizmo g : board.getGizmos()) {
                        if (g.getX() == x && g.getY() == y) {
                            return false;
                        }
                    }

                }
                else {
                    //if the names are equal
                    return false;
                }
            }
            Ball ball = new Ball(x, y, xv, yv, name);
            board.addBall(ball);
            details.addBall(ball);
            BoardState.add("Add " + name + " " + x + " " + y + " " + xv + " " + yv);
            return true;
        }
       // return false; UNREACHABLE STATEMENT - NECESSARY?
    }

    public void removeGizmo(String name) { //TODO: check if exists
        BoardState.add("Delete " + name);
        board.removeGizmo(getGizmo(name));
    }

    public void removeGizmo(double x, double y) {  //TODO: check if exists
        Gizmo gizmo = getGizmo(x, y);
        BoardState.add("Delete " + gizmo.getName());
        board.removeGizmo(gizmo);
    }

    public boolean removeBall(String name) {
        for(Ball b: board.getBalls()) {
            if (b.getName().equals(name)) {
                BoardState.add("Delete " + name);
                board.removeBall(getBall(name)); //TODO: check this method of getting the ball as returns null
                return true;
            }
        }
        return false;
    }

//    public boolean removeBall(double x, double y) { //TODO: check if exists
//        for(Ball b: board.getBalls()) {
//            if (b.getX() == x && b.getY() == y){
//                Ball ball = getBall(x, y);
//                BoardState.add("Delete " + ball.getName());
//                board.removeBall(ball);
//                return true;
//            }
//        }
//        return false;
//    }

    public void clearBoard() {
        board.clear();
    }

    public boolean moveGizmo(String name, double newX, double newY) {
        for(Gizmo g: board.getGizmos()){
            if(g.getName().equals(name)){
                BoardState.add("Move " + name + " " + newX + " " + newY);
                Gizmo gizmo = getGizmo(name);
                gizmo.setCoordinates(newX,newY);

                if (g.getBoundingBox().isIntersecting(gizmo.getBoundingBox())){
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    public void moveGizmo(double x, double y, double newX, double newY) { //TODO: check if new position is valid
        Gizmo gizmo = getGizmo(x,y);
        BoardState.add("Move " + gizmo.getName() + " " + newX + " " + newY);
        gizmo.setCoordinates(newX, newY);
    }

    public void moveBall(String name, double newX, double newY) { //TODO: check if new position is valid
        BoardState.add("Move " + name + " " + newX + " " + newY);
        getBall(name).setX(newX);
        getBall(name).setY(newY);
    }

    public void moveBall(double x, double y, double newX, double newY) { //TODO: check if new position is valid
        Ball ball = getBall(x,y);
        BoardState.add("Move " + ball.getName() + " " + newX + " " + newY);
        ball.setX(newX);
        ball.setY(newY);
    }

    public void rotateGizmo(String name) {
        BoardState.add("Rotate " + name);
        getGizmo(name).rotate();
    }

    public void rotateGizmo(double x, double y) {
        Gizmo gizmo = getGizmo(x,y);
        BoardState.add("Rotate " + gizmo.getName());
        gizmo.rotate();
    }

    public void triggerGizmo(String name) {
        getGizmo(name).trigger();
    }

    public void triggerGizmo(double x, double y) {
        getGizmo(x, y).trigger();
    }

    public void setGravity(double yVelocity) { //TODO: probably check upper and lower bounds
        this.gravity = new Vect(0, yVelocity);
    }
    public void setFriction(double mu, double mu2) { //TODO: probably check upper and lower bounds
        this.frictionMU = mu;
        this.frictionMU2 = mu2;
    }

    public List<Gizmo> getGizmos(){ //TODO: Remove? Maybe return a copy
        return board.getGizmos();
    }

    public List<Ball> getBalls() { //TODO: Remove? Maybe return a copy
//        return ball;
        return board.getBalls();
    }

    public Gizmo getGizmo(String name) {
        for(Gizmo gizmo : board.getGizmos()) {
            if(gizmo.getName().equals(name)){
                return gizmo;
            }
        }
        return null;
    }

    public Gizmo getGizmo(double x, double y) {
        for(Gizmo gizmo : board.getGizmos()) {
            if(gizmo.getX() == x && gizmo.getY() == y){
                return gizmo;
            }
        }
        return null;
    }


    public Ball getBall(String name) {
        for(Ball ball : board.getBalls()) {
            System.out.println(ball.getName());
            if(ball.getName().equals(name)){
                return ball;
            }
        }
        return null;
    }

    public Ball getBall(double x, double y) {
        for(Ball ball : board.getBalls()) {
            if(ball.getX() == x && ball.getY() == y){
                return ball;
            }
        }
        return null;
    }
}
