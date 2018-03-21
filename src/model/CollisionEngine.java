package model;

import model.board.Ball;
import model.board.Board;
import model.board.Walls;
import model.board.gizmos.AbsorberGizmo;
import model.board.gizmos.FlipperGizmo;
import model.board.gizmos.Gizmo;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.board.BoardObjectType.ABSORBER;
import static model.board.BoardObjectType.LEFT_FLIPPER;
import static model.board.BoardObjectType.RIGHT_FLIPPER;
import static util.Constants.FRICTION_MU;
import static util.Constants.FRICTION_MU_2;
import static util.Constants.GRAVITY;

public class CollisionEngine {

    private double frictionMU2;
    private double frictionMU;
    private Vect gravity;
    private Map<Ball, CollisionDetails> collisionDetailsMap;
    private Board board;

    CollisionEngine(Board board){
        this.board = board;
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;

        collisionDetailsMap = new HashMap<>();
    }

    public void addBall(Ball ball) {
        System.out.println("added " + ball);
        collisionDetailsMap.put(ball, new CollisionDetails());
    }

    public void removeBall(Ball ball){
        System.out.println("removed " + ball);
        collisionDetailsMap.remove(ball);
    }


    public void collide(double moveTime) {
        findTimeUntilCollision();

        for (Ball ball : board.getBalls()) {
            CollisionDetails details = getCollisionDetails(ball);
            details.setCollidedBoardObject(null);

            if (!ball.isInAbsorber()) {
                if (details.getTimeUntilCollision() > moveTime) {
                    ball.moveForTime(moveTime);
                    applyGravity(ball, moveTime);
                    applyFriction(ball, moveTime);

                } else {
                    System.out.println("gonna collide");
                    if (details.getPotentialCollision() != null) {
                        System.out.println(details.getPotentialCollision());
                        if (details.getPotentialCollision().getType() == ABSORBER) {
                            ((AbsorberGizmo) details.getPotentialCollision()).addBall(ball);
                            System.out.println("BALL ADDED: " + ball.getName());
                        }
                        details.setCollidedBoardObject(details.getPotentialCollision());
                    }
                    ball.moveForTime(details.getTimeUntilCollision());
                    ball.setVelocity(details.getPotentialVelocity()); //TODO: check if works
                    applyGravity(ball, details.getTimeUntilCollision());
                    applyFriction(ball, details.getTimeUntilCollision());
                }
            }
        }
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

    public void setGravity(double yVelocity) { //TODO: probably check upper and lower bounds
        this.gravity = new Vect(0, yVelocity);
    }

    public void setFriction(double mu, double mu2) { //TODO: probably check upper and lower bounds
        this.frictionMU = mu;
        this.frictionMU2 = mu2;
    }

    private void findTimeUntilGizmoCollision(Gizmo gizmo, Ball ball) {
        double time;
        List<LineSegment> lines = gizmo.getLines();
        List<Circle> circles = gizmo.getCircles();
        Vect velocity;
        CollisionDetails details = getCollisionDetails(ball);

        double timeUntilCollision = details.getTimeUntilCollision();
        Circle ballCircle = ball.getCircles().get(0);

        if ((gizmo.getType() == LEFT_FLIPPER || gizmo.getType() == RIGHT_FLIPPER)) {
            //TODO: hopefully remove the casting, probably through interface for moving gizmos or maybe reduce it to one cast at the start

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity()); //TODO: Fix flipper collision
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    details.setPotentialVelocity(velocity);
                    details.setPotentialCollision(gizmo);
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), ((FlipperGizmo)gizmo).getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    details.setPotentialVelocity(velocity);
                    details.setPotentialCollision(gizmo);
                }
            }
        }
        else {
            for (LineSegment line : lines) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectWall(line, ball.getVelocity(), gizmo.getRCoefficient());
                    details.setPotentialVelocity(velocity);
                    details.setPotentialCollision(gizmo);
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), gizmo.getRCoefficient());
                    details.setPotentialVelocity(velocity);
                    details.setPotentialCollision(gizmo);
                }
            }
        }

        details.setTimeUntilCollision(timeUntilCollision);

    }

    private void findTimeUntilWallsCollission(Walls walls, Ball ball) {
        double time;
        List<LineSegment> lines = walls.getLines();
        List<Circle> circles = walls.getCircles();
        Vect velocity;
        CollisionDetails details = getCollisionDetails(ball);

        double timeUntilCollision = details.getTimeUntilCollision();
        Circle ballCircle = ball.getCircles().get(0);

        for (LineSegment line : lines) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectWall(line, ball.getVelocity(), walls.getRCoefficient());
                details.setPotentialVelocity(velocity);
                details.setPotentialCollision(walls);
            }
        }

        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ballCircle, ball.getVelocity());
            if (time < timeUntilCollision) {
                timeUntilCollision = time;
                velocity = Geometry.reflectCircle(circle.getCenter(), ball.getCenter(), ball.getVelocity(), walls.getRCoefficient());
                details.setPotentialVelocity(velocity);
                details.setPotentialCollision(walls);
            }
        }
        details.setTimeUntilCollision(timeUntilCollision);
    }

    private void findTimeUntilBallCollision(Ball otherBall, Ball ball) {
        CollisionDetails details = getCollisionDetails(ball);
        CollisionDetails otherDetails = getCollisionDetails(otherBall);

        double timeUntilCollision = details.getTimeUntilCollision();
        double timeUntilCollisionOther = otherDetails.getTimeUntilCollision();
        double time;

        time = Geometry.timeUntilBallBallCollision(ball.getCircles().get(0), ball.getVelocity(), otherBall.getCircles().get(0), otherBall.getVelocity());
        Geometry.VectPair velocities = Geometry.reflectBalls(ball.getCenter(), 1, ball.getVelocity(), otherBall.getCenter(), 1, otherBall.getVelocity());

        if (time < timeUntilCollision) {
            timeUntilCollision = time;
            details.setPotentialVelocity(velocities.v1);
            details.setPotentialCollision(otherBall);

        }

        if (time < timeUntilCollisionOther) {
            timeUntilCollisionOther = time;
            otherDetails.setPotentialVelocity(velocities.v2);
            details.setPotentialCollision(ball);
        }
        details.setTimeUntilCollision(timeUntilCollision);
        otherDetails.setTimeUntilCollision(timeUntilCollisionOther);
    }

    public CollisionDetails getCollisionDetails(Ball ball) {
        return collisionDetailsMap.get(ball);
    }

    void findTimeUntilCollision() {

        Map<Ball, List<Ball>> checked = new HashMap<>();

        board.getBalls().forEach(ball -> {
            getCollisionDetails(ball).setTimeUntilCollision(Double.MAX_VALUE);
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
}
