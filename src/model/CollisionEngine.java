package model;

import model.board.Ball;
import physics.Vect;
import util.BoardState;

import static util.Constants.FRICTION_MU;
import static util.Constants.FRICTION_MU_2;
import static util.Constants.GRAVITY;

public class CollisionEngine {

    private Vect gravity;
    private double frictionMU;
    private double frictionMU2;

    public CollisionEngine(){
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;
    }

    public void applyGravity(Ball ball, double moveTime) {
        ball.setVelocity(ball.getVelocity().plus(gravity.times(moveTime)));

    }

    public void applyFriction(Ball ball, double moveTime) {
        double vOldX = ball.getVelocity().x();
        double vOldY = ball.getVelocity().y();

        double vNewX = vOldX * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldX) * moveTime));
        double vNewY = vOldY * (1 - (frictionMU * moveTime) - (frictionMU2 * Math.abs(vOldY) * moveTime));

        ball.setVelocity(new Vect(vNewX, vNewY));
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
}
