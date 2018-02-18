package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Walls walls;
    private List<Ball> balls;
    private List<Gizmo> gizmos;

    public Board() {
        walls = new Walls();
        balls = new ArrayList<>();
        gizmos = new ArrayList<>();
    }

    public List<Gizmo> getGizmos() {
        return gizmos;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public Walls getWalls() {
        return walls;
    }

    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();

        gizmos.forEach(gizmo -> lines.addAll(gizmo.getLines()));

        lines.addAll(walls.getLines());

        return lines;
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        gizmos.forEach(gizmo -> circles.addAll(gizmo.getCircles()));

        balls.forEach(ball -> circles.add(ball.getCircle()));

        circles.addAll(walls.getCircles());

        return circles;
    }

    public boolean add(Gizmo gizmo) {
        return gizmos.add(gizmo);
    }

    public void add(Ball ball) {
        balls.add(ball);
    }

    public void clear() {
        gizmos.clear();
        balls.clear();
    }

    public void remove(Gizmo gizmo) {
        gizmos.remove(gizmo);
    }
}
