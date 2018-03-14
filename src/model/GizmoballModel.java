package model;

import model.board.Ball;
import model.board.Board;
import model.board.BoardObjectType;
import model.board.Walls;
import model.board.gizmos.*;
import physics.*;
import util.BoardState;
import util.GizmoNames;

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

    private Gizmo collidedGizmo;


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

        for (Ball ball : board.getBalls()) {
            if (!ball.isInAbsorber()) {
                if (details.getTimeUntilCollision(ball) > moveTime) {
                    ball.moveForTime(moveTime);
                } else {
                    if (collidedGizmo != null) {
                        if (collidedGizmo.getType() == ABSORBER) {
                            ((AbsorberGizmo)collidedGizmo).addBall(ball);
                        }
                        if (collidedGizmo.getType() == BALL) {
                            System.out.println("BALL COLLISION!");
                        }
                    }
                    ball.moveForTime(details.getTimeUntilCollision(ball)); //TODO: Fix issue where ball velocity becomes too low and stops abruptly
                    ball.applyPotentialVelocity();
                }
            }
        }

    }
    
    private void findTimeUntilGizmoCollision(Gizmo gizmo, Ball ball) {
        double time;
        List<LineSegment> lines = gizmo.getLines();
        List<Circle> circles = gizmo.getCircles();
        Vect velocity;
        double timeUntilCollision = details.getTimeUntilCollision(ball);
        Circle ballCircle = ball.getCircles().get(0);

        if ((gizmo.getType() == LEFT_FLIPPER || gizmo.getType() == RIGHT_FLIPPER) && ((FlipperGizmo)gizmo).isMoving()) {
            //TODO: hopefully remove the casting, probably through interface for moving gizmos or maybe reduce it to one cast at the start

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity()); //TODO: Fix flipper collision
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo;
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo;
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
                    collidedGizmo = gizmo;
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                    ball.setPotentialVelocity(velocity);
                    collidedGizmo = gizmo;
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

    //TODO: Maybe move position checking of add and move to the board, or at least a private method
    public boolean addGizmo(double x, double y, String name, BoardObjectType type) {
        Gizmo gizmo;
        String gizmoName = name;
        for(Gizmo g: getGizmos()) {
            if(g.getName().equals(name)) {
                return false;
            }
        }
        switch (type) {
            case CIRCLE:
                if(name.equals("")) {
                   gizmoName = GizmoNames.addName(CIRCLE);
                }

                gizmo = new CircleGizmo(x, y, ONE_L, gizmoName);
                break;
            case SQUARE:
                if(name.equals("")) {
                    gizmoName = GizmoNames.addName(SQUARE);
                }

                gizmo = new SquareGizmo(x, y, ONE_L, gizmoName);
                break;
            case TRIANGLE:
                if(name.equals("")) {
                    gizmoName = GizmoNames.addName(TRIANGLE);
                }

                gizmo = new TriangleGizmo(x, y, ONE_L, gizmoName);
                break;
            case LEFT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = GizmoNames.addName(LEFT_FLIPPER);
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            case RIGHT_FLIPPER:
                if(name.equals("")) {
                    gizmoName = GizmoNames.addName(RIGHT_FLIPPER);
                }

                gizmo = new FlipperGizmo(x, y, 0, type, gizmoName);
                break;
            default:
                return false;
        }


        if(isIntersecting(gizmo)) {
            return false;
        }

        board.addGizmo(gizmo);
        BoardState.add(type + " " + gizmoName + " " + x + " " + y);
        return true;
    }

    public boolean addAbsorber(double x, double y, double x2, double y2, String name){
        if(name.equals("")) {
            name = GizmoNames.addName(ABSORBER);
        }
        if (board.getGizmos().isEmpty()){
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            board.addGizmo(absorber);
            //TODO: should we add a details.addGizmo(?) here?
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y + " " + x2 + " " + y2);
            return true;
        }
        else{
            Gizmo absorber = new AbsorberGizmo(x, y, x2, y2, name);
            if (isIntersecting(absorber)) {
                return false;
            }
//            for (Gizmo gizmo : board.getGizmos()) {
//                if (absorber.getBoundingBox().isIntersecting(gizmo.getBoundingBox())){
//                    return false;
//                }
//                if (gizmo.getName().equals(name)){
//                    return false;
//                }
//            }
            board.addGizmo(absorber);
            BoardState.add(ABSORBER.toString() + " " + name + " " + x + " " + y + " " + x2 + " " + y2);
            return true;
        }
    }


    public boolean addBall(double x, double y, double xv, double yv, String name) {
        String ballName = name;
        if(name.equals("")){
            ballName = GizmoNames.addName(BALL);
        }
        if (board.getBalls().isEmpty()){

            Ball ball = new Ball(x, y, xv, yv, ballName);
            details.addBall(ball);
            if(isBallIntersecting(ball)){
                details.removeBall(ball);
                return false;
            }
            board.addBall(ball);
            BoardState.add("Add " + ballName + " " + x + " " + y + " " + xv + " " + yv);
            return true;
        }
        else {
            Ball ball = new Ball(x, y, xv, yv, ballName);
            details.addBall(ball);
            for (Ball b : board.getBalls()) {
                //if name of ball being added is NOT equal to the name of some other ball
                if (!b.getName().equals(name)) {
                    if (b.getX() == x && b.getY() == y) {
                        details.removeBall(ball);
                        return false;
                    }
                    if(isBallIntersecting(b)){
                        details.removeBall(ball);
                        return false;
                    }
                    for (Gizmo g : board.getGizmos()) {
                        if (g.getX() == x && g.getY() == y) {
                            details.removeBall(ball);
                            return false;
                        }
                    }

                }
                else {
                    //if the names are equal
                    return false;
                }
            }
            board.addBall(ball);
            BoardState.add("Add " + ballName + " " + x + " " + y + " " + xv + " " + yv);
            return true;
        }
        // return false; UNREACHABLE STATEMENT - NECESSARY?
    }

    public boolean removeGizmo(String name) {
        for(Gizmo g: getGizmos()) {
            if(g.getName().equals(name)){
                BoardState.add("Delete " + name);
                board.removeGizmo(getGizmo(name));
                return true;
            }
        }
        return false;
    }

    public boolean removeGizmo(double x, double y) {
        for(Gizmo g: getGizmos()){
            if(g.getX()==x && g.getY()==y) {
                Gizmo gizmo = getGizmo(x, y);
                BoardState.add("Delete " + gizmo.getName());
                board.removeGizmo(gizmo);
                return true;
            }
        }
        return false;
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

    public boolean removeBall(double x, double y) {
        for(Ball b: board.getBalls()) {
            double radius = b.getDiameter()/2;
            if (Math.pow(x - b.getX(),2) + Math.pow(y - b.getY(), 2) < Math.pow(radius, 2)){
                Ball ball = getBall(b.getX(), b.getY());
                if(ball!=null) {
                    BoardState.add("Delete " + ball.getName());
                    board.removeBall(ball);
                    return true;
                }
            }
        }
        return false;
    }

    public void clearBoard() {
        board.clear();
        BoardState.removeAll();
    }

    public boolean moveGizmo(String name, double newX, double newY) {
        Gizmo gizmo = getGizmo(name);
        if(gizmo != null) {
            double x = gizmo.getX();
            double y = gizmo.getY();
            gizmo.setCoordinates(newX, newY);
            if (!isIntersecting(gizmo)) {
                BoardState.add("Move " + gizmo.getName() + " " + newX + " " + newY);
                return true;
            }
            gizmo.setCoordinates(x, y);
        }
        return false;
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

    public boolean isIntersecting(Gizmo gizmo) {
        for (Gizmo g : getGizmos()){
            if (gizmo.getBoundingBox().isIntersecting(g.getBoundingBox())) {
                if (gizmo != g) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveBall(String name, double newX, double newY) { //TODO: check if new position is valid
        Ball ball = getBall(name);
        if(ball!=null) {
            double x = ball.getX();
            double y = ball.getY();
            ball.setX(newX);
            ball.setY(newY);
            if (!isBallIntersecting(ball)) {
                BoardState.add("Move " + name + " " + newX + " " + newY);
                return true;
            }
            ball.setX(x);
            ball.setY(y);
        }
        return false;
    }

    public void moveBall(double x, double y, double newX, double newY) { //TODO: check if new position is valid
        Ball ball = getBall(x,y);
        BoardState.add("Move " + ball.getName() + " " + newX + " " + newY);
        ball.setX(newX);
        ball.setY(newY);
    }

    private boolean isBallIntersecting(Ball ball){
        double x = ball.getX(); //get x from ball
        double y = ball.getY(); //get y from ball

        //TODO: these variables seem unnecessary
        int roundX = (int) x; //x as int
        int roundY = (int) y; //y as int
        Vect orig = ball.getVelocity();

//        if(getGizmos() == null){
//            System.out.println("false");
//            System.out.println(ball.getName());
//            return false;
//        }

        for (Gizmo gizmo: getGizmos()) {
            ball.setVelocity(new Vect(0.01 + (ball.getDiameter()/2), 0.0));
            findTimeUntilGizmoCollision(gizmo, ball);
            System.out.println(details.getTimeUntilCollision(ball));
            if((int) details.getTimeUntilCollision(ball) < 1){
                ball.setVelocity(orig);
                System.out.println("true");
                System.out.println(ball.getName());
                return true;
            }
            ball.setVelocity(new Vect(-0.01 - (ball.getDiameter()/2), 0.0));
                    findTimeUntilGizmoCollision(gizmo, ball);
                    System.out.println(details.getTimeUntilCollision(ball));
                    if((int) details.getTimeUntilCollision(ball) < 1){
                        ball.setVelocity(orig);
                        System.out.println("true");
                        System.out.println(ball.getName());
                        return true;
                    }
            ball.setVelocity(new Vect(0.0, 0.01 + (ball.getDiameter()/2)));
                    findTimeUntilGizmoCollision(gizmo, ball);
                    System.out.println(details.getTimeUntilCollision(ball));
                    if((int) details.getTimeUntilCollision(ball) < 1){
                        ball.setVelocity(orig);
                        System.out.println("true");
                        System.out.println(ball.getName());
                        return true;
                    }
            ball.setVelocity(new Vect(0.0, -0.01 - (ball.getDiameter()/2)));
                    findTimeUntilGizmoCollision(gizmo, ball);
                    System.out.println(details.getTimeUntilCollision(ball));
                    if((int) details.getTimeUntilCollision(ball) < 1){
                        ball.setVelocity(orig);
                        System.out.println("true");
                        System.out.println(ball.getName());
                        return true;
                    }
        }

        System.out.println("false");
        System.out.println(ball.getName());
        ball.setVelocity(orig);
        return false;

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
