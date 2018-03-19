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
    private String errorMessage = "";
    private GizmoNames gizmoNames;
    private CollisionEngine collisionEngine;

    public GizmoballModel() {
        board = new Board();
        details = new CollisionDetails();
        gizmoNames = new GizmoNames();
    }

    public Board getBoard(){
        return board;
    }

    public CollisionDetails getDetails() {
        return details;
    }

    public void activateGizmoActions() {

        for (Gizmo gizmo : getGizmos()) {

            gizmo.activateAction();

        }

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
            if(!isOutside(gizmo)){
                if (!isIntersecting(gizmo)) {
                    BoardState.add("Move " + gizmo.getName() + " " + newX + " " + newY);
                    return true;
                } else {
                    setMessage("Cannot move gizmo within another gizmo.");//TODO make this error work cos it just doesnt
                }
            } else {
                System.out.println("You made it here");
                setMessage("Cannot move a gizmo outside of the playable board."); //TODO This one too
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

    private boolean isOutside(Gizmo gizmo){
        return gizmo.getBoundingBox().isOutside();
    }

    private boolean ballVelocityIntersectionCheck(double x, double y, Ball ball){
        ball.setVelocity(new Vect(x,y));
        collisionEngine.findTimeUntilCollision();
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
