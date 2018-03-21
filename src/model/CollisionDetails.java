package model;

import model.board.BoardObject;
import model.board.gizmos.Gizmo;
import physics.Vect;

public class CollisionDetails {
    private BoardObject potentialCollision;
    private BoardObject collidedBoardObject;
    private Vect potentialVelocity;
    private double timeUntilCollision;

    public CollisionDetails() {

    }

    public BoardObject getPotentialCollision() {
        return potentialCollision;
    }

    public void setPotentialCollision(BoardObject potentialCollision) {
        this.potentialCollision = potentialCollision;
        System.out.println("Setting pot collision: " + potentialCollision);
    }

    public BoardObject getCollidedBoardObject() {
        return collidedBoardObject;
    }

    public void setCollidedBoardObject(BoardObject collidedBoardObject) {
        System.out.println("Collided with: " + collidedBoardObject);
        this.collidedBoardObject = collidedBoardObject;
    }

    public Vect getPotentialVelocity() {
        return potentialVelocity;
    }

    public void setPotentialVelocity(Vect potentialVelocity) {
        this.potentialVelocity = potentialVelocity;
    }

    public double getTimeUntilCollision() {
        return timeUntilCollision;
    }

    public void setTimeUntilCollision(double timeUntilCollision) {
        this.timeUntilCollision = timeUntilCollision;
    }


    //
//    public void setTimeUntilCollission(Ball ball, double timeUntilCollission) {
//        details.put(ball, timeUntilCollission);
//    }
//
//    public double getTimeUntilCollision(Ball ball) {
//        return details.get(ball);
//    }
//
//    public void addBall(Ball ball) {
//        System.out.println("added " + ball);
//        details.put(ball, Double.MAX_VALUE);
//    }
//
//    public void removeBall(Ball ball){
//        System.out.println("removed " + ball);
//        details.remove(ball);
//    }
}
