package model;

import model.board.Ball;
import model.board.Board;
import model.board.BoardObjectType;
import model.board.Walls;
import model.board.gizmos.*;
import physics.*;
import util.BoardState;
import util.GizmoNames;
import util.Triggers;

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

    private Gizmo potentialCollision;
    private Gizmo collidedGizmo;
    private String errorMessage = "";
    private GizmoNames gizmoNames;

    public GizmoballModel() {
        board = new Board();
        details = new CollisionDetails();
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;
        gizmoNames = new GizmoNames();
    }

    public void moveBalls() {
        double moveTime = SECONDS_PER_FRAME;

        findTimeUntilCollision();

        for (Ball ball : board.getBalls()) {
            if (!ball.isInAbsorber()) {
                if (details.getTimeUntilCollision(ball) > moveTime) {
                    ball.moveForTime(moveTime);
                    applyGravity(ball, moveTime);
                    applyFriction(ball, moveTime);

                } else {
                    if (potentialCollision != null) {
                        if (potentialCollision.getType() == ABSORBER) {
                            ((AbsorberGizmo) potentialCollision).addBall(ball);
                        }
                        collidedGizmo = potentialCollision;
                    }
                    ball.moveForTime(details.getTimeUntilCollision(ball));
                    ball.applyPotentialVelocity();
                    applyGravity(ball, details.getTimeUntilCollision(ball));
                    applyFriction(ball, details.getTimeUntilCollision(ball));
                }
            }
        }

        sendTriggers();
        activateGizmoActions();
        collidedGizmo = null;
        potentialCollision = null;
    }

    private void applyGravity(Ball ball, double moveTime) {
        ball.setVelocity(ball.getVelocity().plus(gravity.times(moveTime)));

    }

    private void applyFriction(Ball ball, double moveTime) {
        double vOldX = ball.getVelocity().x();
        double vOldY = ball.getVelocity().y();

        double vNewX = vOldX * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldX) * moveTime));
        double vNewY = vOldY * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldY) * moveTime));

        ball.setVelocity(new Vect(vNewX, vNewY));
    }

    private void sendTriggers() { //TODO: maybe move from here to outside the model
        if (collidedGizmo != null) {
            for (Gizmo gizmo : Triggers.getTriggeredGizmos(collidedGizmo)) {
                gizmo.trigger(false);
            }
        }
    }

    private void activateGizmoActions() {

        for (Gizmo gizmo : getGizmos()) {

            gizmo.activateAction();

        }

    }


    private void findTimeUntilGizmoCollision(Gizmo gizmo, Ball ball) {
        double time;
        List<LineSegment> lines = gizmo.getLines();
        List<Circle> circles = gizmo.getCircles();
        Vect velocity;
        double timeUntilCollision = details.getTimeUntilCollision(ball);
        Circle ballCircle = ball.getCircles().get(0);

        if ((gizmo.getType() == LEFT_FLIPPER || gizmo.getType() == RIGHT_FLIPPER)) {
            //TODO: hopefully remove the casting, probably through interface for moving gizmos or maybe reduce it to one cast at the start

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity()); //TODO: Fix flipper collision
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    System.out.println(velocity);
                    ball.setPotentialVelocity(velocity);
                    potentialCollision = gizmo;
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    potentialCollision = gizmo;
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
                    potentialCollision = gizmo;
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    potentialCollision = gizmo;
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
        double timeUntilCollision = details.getTimeUntilCollision(ball);
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
        double timeUntilCollision = details.getTimeUntilCollision(ball);
        double timeUntilCollisionOther = details.getTimeUntilCollision(otherBall);
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
            board.getGizmos().forEach(gizmo -> findTimeUntilGizmoCollision(gizmo, ball));

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

    public boolean addGizmo(double x, double y, String name, BoardObjectType type) {
        Gizmo gizmo;
        String gizmoName = name;
        if (!name.equals("") && !gizmoNames.addName(gizmoName)) {
            return false;
        }
        switch (type) {
            case CIRCLE:
                if(name.equals("")) {
                    gizmoName = gizmoNames.addName(CIRCLE);
                }

                gizmo = new CircleGizmo(x, y, ONE_L, gizmoName);
                break;
            case SQUARE:
                if(name.equals("")) {
                    gizmoName = gizmoNames.addName(SQUARE);
                }

                gizmo = new SquareGizmo(x, y, ONE_L, gizmoName);
                break;
            case TRIANGLE:
                if(name.equals("")) {
                    gizmoName = gizmoNames.addName(TRIANGLE);
                }

                gizmo = new TriangleGizmo(x, y, ONE_L, gizmoName);
                break;
            case LEFT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = gizmoNames.addName(LEFT_FLIPPER);
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            case RIGHT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = gizmoNames.addName(RIGHT_FLIPPER);
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            default:
                return false;
        }
        if(isIntersecting(gizmo)) {
           setMessage("Gizmo cannot be placed on top of another gizmo.");
            return false;
        }
        board.addGizmo(gizmo);
        BoardState.add(type + " " + gizmoName + " " + x + " " + y);
        return true;
    }

    public boolean addAbsorber(double x, double y, double x2, double y2, String name){
        if(name.equals("")) {
            name = gizmoNames.addName(ABSORBER);
        }
        else if (!gizmoNames.addName(name)) {
            return false;
        }

        if (board.getGizmos().isEmpty()){
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            board.addGizmo(absorber);
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y + " " + x2 + " " + y2);
            return true;
        }
        else{
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            if (isIntersecting(absorber)) {
                setMessage("Absorber cannot intersect with any existing gizmos");
                return false;
            }
            board.addGizmo(absorber);
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y + " " + x2 + " " + y2);
            return true;
        }
    }

    public boolean addBall(double x, double y, double xv, double yv, String name) {
        String ballName = name;
        if(name.equals("")){
            ballName = gizmoNames.addName(BALL);
        }
        else if (!gizmoNames.addName(name)){
            return false;
        }

        Ball ball = new Ball(x, y, xv, yv, ballName);
        details.addBall(ball);
        board.addBall(ball);
        if(isBallIntersecting(ball)){
            setMessage("Ball cannot be intersecting with any gizmos.");
            details.removeBall(ball);
            board.removeBall(ball);
            return false;
        }
        BoardState.add("Ball " + ballName + " " + x + " " + y + " " + xv + " " + yv);
        return true;
    }

    public boolean removeGizmo(String name) {
        Gizmo gizmo = getGizmo(name);
        return gizmo != null && removeGizmo(gizmo.getX(), gizmo.getY());
    }

    public boolean removeGizmo(double x, double y) {
        Gizmo gizmo = getGizmo(x,y);
        if (gizmo != null) {
            BoardState.add("Delete " + gizmo.getName());
            board.removeGizmo(gizmo);
            return true;
        }
        return false;
    }

    public boolean removeBall(String name) {
        Ball ball = getBall(name);
        return ball != null && removeBall(ball.getX(), ball.getY());
    }

    public boolean removeBall(double x, double y) {
        Ball ball = getBall(x,y);
        if (ball != null) {
            BoardState.add("Delete " + ball.getName());
            board.removeBall(ball);
            return true;
        }
        return false;
    }

    public boolean moveGizmo(String name, double newX, double newY) {
        Gizmo gizmo = getGizmo(name);
        return gizmo!= null && moveGizmo(gizmo.getX(), gizmo.getY(), newX, newY);
    }

    public boolean moveGizmo(double x, double y, double newX, double newY) {
        Gizmo gizmo = getGizmo(x,y);
        if(gizmo != null) {
            gizmo.setCoordinates(newX, newY);
            if (!isIntersecting(gizmo)) {
                BoardState.add("Move " + gizmo.getName() + " " + newX + " " + newY);
                return true;
            }
            gizmo.setCoordinates(x, y);
        }
        return false;
    }

    public boolean moveBall(String name, double newX, double newY) {
        Ball ball = getBall(name);
        return ball != null && moveBall(ball.getX(), ball.getY(), newX, newY);
    }

    public boolean moveBall(double x, double y, double newX, double newY) {
        Ball ball = getBall(x,y);
        if(ball!=null) {
            ball.setX(newX);
            ball.setY(newY);
            if (!isBallIntersecting(ball)) {
                BoardState.add("Move " + ball.getName() + " " + newX + " " + newY);
                return true;
            }
            ball.setX(x);
            ball.setY(y);
        }
        return false;
    }

    public boolean rotateGizmo(String name) {
        Gizmo gizmo = getGizmo(name);
        return gizmo != null && rotateGizmo(gizmo.getX(), gizmo.getY());
    }

    public boolean rotateGizmo(double x, double y) {
        Gizmo gizmo = getGizmo(x,y);
        if(gizmo != null){
            if(gizmo.getType() == ABSORBER){
                setMessage("You cannot rotate an absorber."); //TODO add the set message feature in rotate handler - doesn't properly work atm
                return false;
            }
            BoardState.add("Rotate " + gizmo.getName());
            gizmo.rotate();
            return true;
        }
        return false;
    }

    public void clearBoard() {
        board.clear();
        BoardState.removeAll();
        gizmoNames.resetNames();
    }

    public void setGravity(double yVelocity) { //TODO: probably check upper and lower bounds
        this.gravity = new Vect(0, yVelocity);
        System.out.println("gravity changed: " + this.gravity);
        BoardState.add("Gravity " + yVelocity);
    }

    public void setFriction(double mu, double mu2) { //TODO: probably check upper and lower bounds
        this.frictionMU = mu;
        this.frictionMU2 = mu2;
        BoardState.add("Friction " + mu + " " + mu2);
    }

    public void setFrictionMU(double mu) {
        setFriction(mu, frictionMU2);
    }
    public void setFrictionMU2(double mu2) {
        setFriction(frictionMU, mu2);
    }


    public List<Gizmo> getGizmos(){ //TODO: Remove? Maybe return a copy
        return board.getGizmos();
    }

    public List<Ball> getBalls() { //TODO: Remove? Maybe return a copy
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
            if(ball.getName().equals(name)){
                return ball;
            }
        }
        return null;
    }

    public Ball getBall(double x, double y) {
        for(Ball ball : board.getBalls()) {
            double radius = ball.getDiameter()/2;
            if (Math.pow(x - ball.getX(),2) + Math.pow(y - ball.getY(), 2) < Math.pow(radius, 2)){
                return ball;
            }
        }
        return null;
    }

    public void setMessage(String s){
        errorMessage = s;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean isIntersecting(Gizmo gizmo) {
        for (Gizmo g : getGizmos()){
            if (gizmo.getBoundingBox().isIntersecting(g.getBoundingBox())) {
                if (gizmo != g) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ballVelocityIntersectionCheck(double x, double y, Ball ball){
        ball.setVelocity(new Vect(x,y));
        findTimeUntilCollision();
        System.out.println(ball.getName());
        System.out.println(details.getTimeUntilCollision(ball));
        if (details.getTimeUntilCollision(ball) < 0.3){
            return true;
        }
        return false;
    }

    private boolean isBallIntersecting(Ball ball){
        Vect orig = new Vect(ball.getVelocity().x(),ball.getVelocity().y());
        //LEFT
        if(ballVelocityIntersectionCheck(0.1 + (ball.getDiameter()/2),0.0, ball)){
            return true;
        }
        //RIGHT
        if(ballVelocityIntersectionCheck(-0.1 - (ball.getDiameter()/2),0.0, ball)){
            return true;
        }
        //DOWN
        if(ballVelocityIntersectionCheck(0.0, -0.1 - (ball.getDiameter()/2), ball)){
            return true;
        }
        //DOWN
        if(ballVelocityIntersectionCheck(0.0, 0.1 + (ball.getDiameter()/2), ball)){
            return true;
        }

        ball.setVelocity(orig);

        return false;
    }
}
