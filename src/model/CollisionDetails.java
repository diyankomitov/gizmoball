package model;

import model.board.BoardObject;
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
    }

    public BoardObject getCollidedBoardObject() {
        return collidedBoardObject;
    }

    public void setCollidedBoardObject(BoardObject collidedBoardObject) {
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
}
