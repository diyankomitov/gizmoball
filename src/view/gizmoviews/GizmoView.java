package view.gizmoviews;

import javafx.scene.Node;
import util.Observer;

public interface GizmoView extends Observer {
    double getX();
    double getY();
    void setCoordinates(double x, double y);
    Node getNode();
    void setSelected(boolean selected);
}
