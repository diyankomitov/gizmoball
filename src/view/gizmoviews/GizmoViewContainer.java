package view.gizmoviews;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class GizmoViewContainer extends Pane implements GizmoView{

    protected final Gizmo gizmo;

    GizmoViewContainer(Gizmo gizmo) {
        this.gizmo = gizmo;
        if (gizmo != null) {
            this.gizmo.subscribe(this);
            update();
        }
    }

    @Override
    public double getX() {
        return this.getTranslateX();
    }

    @Override
    public double getY() {
        return this.getTranslateY();
    }

    @Override
    public void setCoordinates(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void update() {
        setCoordinates(gizmo.getX() * ONE_L_IN_PIXELS, gizmo.getY() * ONE_L_IN_PIXELS);
    }
}
