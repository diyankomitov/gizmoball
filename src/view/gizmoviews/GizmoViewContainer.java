package view.gizmoviews;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.board.gizmos.Gizmo;
import view.GlobalLighting;

import static util.Constants.ONE_L_IN_PIXELS;

public class GizmoViewContainer extends Pane implements GizmoView{

    protected final Gizmo gizmo;
    private boolean canFocus;
    protected Shape shape;
    private boolean selected;

    GizmoViewContainer(Gizmo gizmo) {
        this.gizmo = gizmo;
        if (gizmo != null) {
            this.gizmo.subscribe(this);
            update();
        }

        this.canFocus = false;

        this.getStyleClass().add("gizmoContainer");
        this.setOnMouseClicked(event -> {
            if (canFocus) {
                this.requestFocus();
            }
        });
        this.setPickOnBounds(true);
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
    public void setSelected(boolean selected) {
        if (selected) {
            this.getStyleClass().add("selected");
        }
        else {
            this.getStyleClass().remove("selected");
        }
    }

    @Override
    public void update() {
        this.setCoordinates(gizmo.getX() * ONE_L_IN_PIXELS, gizmo.getY() * ONE_L_IN_PIXELS);
    }

    public void setCanFocus(boolean canFocus) {
        this.canFocus = canFocus;
    }
}
