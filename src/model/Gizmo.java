package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.List;

public interface Gizmo extends BoardObject {

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
