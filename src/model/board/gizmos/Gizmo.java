package model.board.gizmos;

import model.BoundingBox;
import model.board.BoardObject;
import util.Observable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface Gizmo extends BoardObject, Observable {

    void setCoordinates(double x, double y);

    double getRCoefficient();

    void rotate();

    double getAngle();

    void trigger();

    void sendTrigger();

    BoundingBox getBoundingBox();
}
