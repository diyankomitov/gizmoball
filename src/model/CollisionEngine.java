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

class CollisionEngine {

    private double frictionMU2;
    private double frictionMU;
    private Vect gravity;
    private final Map<Ball, CollisionDetails> collisionDetailsMap;
    private final Board board;

    CollisionEngine(Board board){
        this.board = board;
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;

        collisionDetailsMap = new HashMap<>();
    }

    public void addBall(Ball ball) {
        collisionDetailsMap.put(ball, new CollisionDetails());
    }

    public void removeBall(Ball ball){
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
                    if (details.getPotentialCollision() != null) {
                        if (details.getPotentialCollision().getType() == ABSORBER) {
                            ((AbsorberGizmo) details.getPotentialCollision()).addBall(ball);
                        }
                        details.setCollidedBoardObject(details.getPotentialCollision());
                    }
                    ball.moveForTime(details.getTimeUntilCollision());
                    ball.setVelocity(details.getPotentialVelocity());
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

    public void setGravity(double yVelocity) {
        this.gravity = new Vect(0, yVelocity);
    }

    public void setFriction(double mu, double mu2) {
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

            FlipperGizmo flipperGizmo = (FlipperGizmo) gizmo;

            for (LineSegment line : lines) {
                time = Geometry.timeUntilRotatingWallCollision(line, gizmo.getCenter(), flipperGizmo.getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingWall(line, gizmo.getCenter(), flipperGizmo.getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
                    details.setPotentialVelocity(velocity);
                    details.setPotentialCollision(gizmo);
                }
            }

            for (Circle circle : circles) {
                time = Geometry.timeUntilRotatingCircleCollision(circle, gizmo.getCenter(), flipperGizmo.getAngularVelocity(), ballCircle, ball.getVelocity());
                if (time < timeUntilCollision) {
                    timeUntilCollision = time;
                    velocity = Geometry.reflectRotatingCircle(circle, gizmo.getCenter(), flipperGizmo.getAngularVelocity(), ballCircle, ball.getVelocity(), gizmo.getRCoefficient());
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

        for (Ball ball1 : board.getBalls()) {
            getCollisionDetails(ball1).setTimeUntilCollision(Double.MAX_VALUE);
        }

        for (Ball ball : board.getBalls()) {
            for (Gizmo gizmo : board.getGizmos()) {
                findTimeUntilGizmoCollision(gizmo, ball);
            }

            findTimeUntilWallsCollission(board.getWalls(), ball);

            checked.put(ball, new ArrayList<>());
            checked.get(ball).add(ball);

            for (Ball otherBall : board.getBalls()) {
                checked.putIfAbsent(otherBall, new ArrayList<>());
                checked.get(otherBall).add(otherBall);
                if (!checked.get(ball).contains(otherBall) && !checked.get(otherBall).contains(ball)) {
                    findTimeUntilBallCollision(otherBall, ball);
                }

                checked.get(ball).add(otherBall);
                checked.get(otherBall).add(ball);
            }
        }
    }
}
