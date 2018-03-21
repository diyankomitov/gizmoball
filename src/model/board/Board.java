package model.board;

import model.board.gizmos.Gizmo;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Walls walls;
    private final List<Ball> balls;
    private final List<Gizmo> gizmos;

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

    public boolean addGizmo(Gizmo gizmo) {
        return gizmos.add(gizmo);
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void clear() {
        gizmos.clear();
        balls.clear();
    }

    public void removeGizmo(Gizmo gizmo) {
        gizmos.remove(gizmo);
    }

    public void removeBall(Ball ball) {
        balls.remove(ball);
    }
}
