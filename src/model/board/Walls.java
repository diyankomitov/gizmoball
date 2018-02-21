package model.board;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class Walls implements BoardObject{

    private final List<LineSegment> lines;
    private final List<Circle> circles;

    public Walls() {
        lines = new ArrayList<>();
        lines.add(new LineSegment(0,0,0,20));
        lines.add(new LineSegment(0,20,20,20));
        lines.add(new LineSegment(20,20,20,0));
        lines.add(new LineSegment(20,0,0,0));

        circles = new ArrayList<>();
        circles.add(new Circle(0,0,0));
        circles.add(new Circle(20,0,0));
        circles.add(new Circle(20,20,0));
        circles.add(new Circle(0,20,0));
    }

    public List<LineSegment> getLines() {
        return lines;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    @Override
    public BoardObjectType getType() {
        return BoardObjectType.WALLS;
    }

    public double getRCoefficient() {
        return 1.0;
    }
}
