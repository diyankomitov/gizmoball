package model.board.gizmos;

import model.board.BoardObject;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import util.Observable;

import java.util.List;

public interface Gizmo extends BoardObject, Observable {

    List<LineSegment> getLines();
    List<Circle> getCircles();

    double getRCoefficient();

    void setCoords(double x, double y);

    String getName();

    double getXCoord();

    double getYCoord();

    boolean isRotating();

    Vect getCenter();

    double getAngularVelocity();
}
