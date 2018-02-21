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

    void setCoordinates(double x, double y);

    double getRCoefficient();

    void rotate();

    double getAngle();

    void trigger();

    void sendTrigger();

}
