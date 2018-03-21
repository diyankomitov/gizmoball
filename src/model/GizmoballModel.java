package model;

import javafx.beans.property.SimpleDoubleProperty;
import model.board.Ball;
import model.board.Board;
import model.board.BoardObject;
import model.board.BoardObjectType;
import model.board.gizmos.*;
import physics.*;
import util.BoardState;
import util.GizmoNames;
import util.Triggers;

import java.util.List;

import static model.board.BoardObjectType.*;
import static util.Constants.*;

public class GizmoballModel{
    private final Board board;
    private final double frictionMU;
    private final double frictionMU2;

    private final SimpleDoubleProperty gravityProperty;
    private final SimpleDoubleProperty frictionMUProperty;
    private final SimpleDoubleProperty frictionMU2Property;

    private String errorMessage = "";
    private final GizmoNames gizmoNames;

    private final CollisionEngine collisionEngine;

    public GizmoballModel() {
        board = new Board();
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;
        gravityProperty = new SimpleDoubleProperty(GRAVITY);
        frictionMUProperty = new SimpleDoubleProperty(FRICTION_MU);
        frictionMU2Property = new SimpleDoubleProperty(FRICTION_MU_2);
        gizmoNames = new GizmoNames();
        collisionEngine = new CollisionEngine(board);
    }

    public void moveBalls() {
        collisionEngine.collide(SECONDS_PER_FRAME);
        sendTriggers();
        activateGizmoActions();
    }

    private void sendTriggers() {
        for (Ball ball : board.getBalls()) {
            BoardObject collidedBoardObject = collisionEngine.getCollisionDetails(ball).getCollidedBoardObject();
            if (collidedBoardObject != null && collidedBoardObject.getType() != WALLS && collidedBoardObject.getType() != BALL) {
                for (Gizmo gizmo : Triggers.getTriggeredGizmos((Gizmo) collidedBoardObject)) {    //TODO: change to include walls
                    gizmo.trigger(false, true);
                }
            }
        }
    }

    private void activateGizmoActions() {
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

        if(isOutside(gizmo)){
            setMessage("Gizmo cannot be placed outside of the board.");
            return false;
        }
        if(isIntersecting(gizmo)) {
            setMessage("Gizmo cannot be placed on top of another gizmo.");
            return false;
        }
//        for(Ball ball : getBalls()){
//            if(isBallIntersecting(ball)){
//                setMessage("Gizmo cannot be placed on top of a ball.");
//            }
//        }
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
        collisionEngine.addBall(ball);
        board.addBall(ball);
        if(isBallIntersecting(ball)){
            setMessage("Ball cannot be intersecting with any gizmos.");
            collisionEngine.removeBall(ball);
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
                    setMessage("Cannot move gizmo within another gizmo.");
                }
            } else {
                setMessage("Cannot move a gizmo outside of the playable board.");
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
                setMessage("You cannot rotate an absorber.");
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

    public void setGravity(double yVelocity) {
        collisionEngine.setGravity(yVelocity);
        BoardState.add("Gravity " + yVelocity);
        gravityProperty.setValue(yVelocity);
    }

    public void setFriction(double mu, double mu2) {
        collisionEngine.setFriction(mu, mu2);
        BoardState.add("Friction " + mu + " " + mu2);
    }

    public void setFrictionMU(double mu) {
        setFriction(mu, frictionMU2);
        frictionMUProperty.setValue(mu);
    }
    public void setFrictionMU2(double mu2) {
        setFriction(frictionMU, mu2);
        frictionMU2Property.setValue(mu2);
    }

    public SimpleDoubleProperty getGravityProperty() {
        return gravityProperty;
    }

    public SimpleDoubleProperty getFrictionMUProperty() {
        return frictionMUProperty;
    }

    public SimpleDoubleProperty getFrictionMU2Property() {
        return frictionMU2Property;
    }

    public List<Gizmo> getGizmos(){
        return board.getGizmos();
    }

    public List<Ball> getBalls() {
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
        board.addGizmo(gizmo);
        for (Ball b : getBalls()){
           if(isBallIntersecting(b)){
               board.removeGizmo(gizmo);
               return true;
           }
        }
        board.removeGizmo(gizmo);
        return false;
    }

    private boolean isOutside(Gizmo gizmo){
        return gizmo.getBoundingBox().isOutside();
    }

    private boolean ballVelocityIntersectionCheck(double x, double y, Ball ball){
        ball.setVelocity(new Vect(x,y));
        collisionEngine.findTimeUntilCollision();
        if (collisionEngine.getCollisionDetails(ball).getTimeUntilCollision() < SECONDS_PER_FRAME) {
            return true;
        }

        Gizmo gizmo = getGizmo(Math.floor(ball.getX()), Math.floor(ball.getY()));

        if (gizmo != null) {
            if (gizmo.getType() == SQUARE) {
                return true;
            }
            if (gizmo.getType() == CIRCLE) {
               double distance = Math.sqrt(Geometry.distanceSquared(ball.getCenter(), gizmo.getCenter()));

               if (distance < ((ball.getDiameter()/2) + (ONE_L/2))) {
                   return true;
               }
            }
        }


            return false;
    }


    private boolean isBallIntersecting(Ball ball){
        Vect orig = new Vect(ball.getVelocity().x(),ball.getVelocity().y());
        //LEFT
        if(ballVelocityIntersectionCheck(0.01,0.0, ball)){
            return true;
        }
        //RIGHT
        if(ballVelocityIntersectionCheck(-0.01,0.0, ball)){
            return true;
        }
        //DOWN
        if(ballVelocityIntersectionCheck(0.0, -0.01, ball)){
            return true;
        }
        //DOWN
        if(ballVelocityIntersectionCheck(0.0, 0.01, ball)){
            return true;
        }

        ball.setVelocity(orig);

        return false;
    }
}
