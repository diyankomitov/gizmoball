package model.board;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.List;

public interface BoardObject {

    BoardObjectType getType();

    String getName();

    double getX();

    double getY();

    List<LineSegment> getLines();

    List<Circle> getCircles();

    Vect getCenter();
}
