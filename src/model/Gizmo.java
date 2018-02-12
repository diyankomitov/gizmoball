package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public interface Gizmo {

    List<LineSegment> getLines();
    List<Circle> getCircles();

    double getRCoefficient();

    void setCoords(double x, double y);

    String getName();
    GizmoType getType();

    double getXCoord();

    double getYCoord();
}
