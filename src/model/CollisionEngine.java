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
import util.Triggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.board.BoardObjectType.ABSORBER;
import static model.board.BoardObjectType.LEFT_FLIPPER;
import static model.board.BoardObjectType.RIGHT_FLIPPER;
import static util.Constants.*;

public class CollisionEngine {

    private Vect gravity;
    private double frictionMU;
    private double frictionMU2;
    private Gizmo potentialCollision;
    private Gizmo collidedGizmo;
    private GizmoballModel gizmoballModel;

    public CollisionEngine(GizmoballModel gizmoballModel){
        gravity = new Vect(0, GRAVITY);
        frictionMU = FRICTION_MU;
        frictionMU2 = FRICTION_MU_2;
        this.gizmoballModel = gizmoballModel;
    }

    public void moveBalls() {
        double moveTime = SECONDS_PER_FRAME;

        findTimeUntilCollision();

        for (Ball ball : gizmoballModel.getBoard().getBalls()) {
//            System.out.println(ball.getName());
            if (!ball.isInAbsorber()) {
                if (gizmoballModel.getDetails().getTimeUntilCollision(ball) > moveTime) {
                    ball.moveForTime(moveTime);
                    applyGravity(ball, moveTime);
                    applyFriction(ball, moveTime);

                } else {
                    if (potentialCollision != null) {
                        System.out.println(potentialCollision.getType());
                        if (potentialCollision.getType() == ABSORBER) {
                            ((AbsorberGizmo) potentialCollision).addBall(ball);
                            System.out.println("BALL ADDED: " + ball.getName());
                        }
                        collidedGizmo = potentialCollision;
                    }
                    ball.moveForTime(gizmoballModel.getDetails().getTimeUntilCollision(ball));
                    ball.applyPotentialVelocity();
                    applyGravity(ball, gizmoballModel.getDetails().getTimeUntilCollision(ball));
                    applyFriction(ball, gizmoballModel.getDetails().getTimeUntilCollision(ball));
                }
            }
        }

        sendTriggers();
        gizmoballModel.activateGizmoActions();
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


    private void findTimeUntilGizmoCollision(Gizmo gizmo, Ball ball) {
        double time;
        List<LineSegment> lines = gizmo.getLines();
        List<Circle> circles = gizmo.getCircles();
        Vect velocity;
        double timeUntilCollision = gizmoballModel.getDetails().getTimeUntilCollision(ball);
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

        gizmoballModel.getDetails().setTimeUntilCollission(ball, timeUntilCollision);

    }

    private void findTimeUntilWallsCollission(Walls walls, Ball ball) {
        double time;
        List<LineSegment> lines = walls.getLines();
        List<Circle> circles = walls.getCircles();
        Vect velocity;
        double timeUntilCollision = gizmoballModel.getDetails().getTimeUntilCollision(ball);
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

        gizmoballModel.getDetails().setTimeUntilCollission(ball, timeUntilCollision);
    }

    private void findTimeUntilBallCollision(Ball otherBall, Ball ball) {
        double timeUntilCollision = gizmoballModel.getDetails().getTimeUntilCollision(ball);
        double timeUntilCollisionOther = gizmoballModel.getDetails().getTimeUntilCollision(otherBall);
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
        gizmoballModel.getDetails().setTimeUntilCollission(ball, timeUntilCollision);
        gizmoballModel.getDetails().setTimeUntilCollission(otherBall, timeUntilCollisionOther);
    }

    public void findTimeUntilCollision() {

        Map<Ball, List<Ball>> checked = new HashMap<>();

        gizmoballModel.getBoard().getBalls().forEach(ball -> {
            gizmoballModel.getDetails().setTimeUntilCollission(ball, Double.MAX_VALUE);
        });

        gizmoballModel.getBoard().getBalls().forEach(ball -> {
            gizmoballModel.getBoard().getGizmos().forEach(gizmo -> findTimeUntilGizmoCollision(gizmo, ball));

            findTimeUntilWallsCollission(gizmoballModel.getBoard().getWalls(), ball);

            checked.put(ball, new ArrayList<>());
            checked.get(ball).add(ball);

            gizmoballModel.getBoard().getBalls().forEach(otherBall -> {
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

