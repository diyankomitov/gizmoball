package view.gizmoviews;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.board.gizmos.Gizmo;
import view.GlobalLighting;

import static util.Constants.ONE_L_IN_PIXELS;

public class GizmoViewContainer extends Pane implements GizmoView, Toggle{

    protected final Gizmo gizmo;
    private boolean canFocus;
    protected Shape shape;
    private BooleanProperty selected;
    private ObjectProperty<ToggleGroup> toggleGroup;
//    private boolean selected;

    GizmoViewContainer(Gizmo gizmo) {
        this.gizmo = gizmo;
        if (gizmo != null) {
            this.gizmo.subscribe(this);
            update();
        }

        this.canFocus = false;

        this.getStyleClass().add("gizmoContainer");
        this.setPickOnBounds(true);

        selected = new SimpleBooleanProperty(false);
        toggleGroup = new SimpleObjectProperty<>(null);
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
    public ToggleGroup getToggleGroup() {
        return toggleGroup.getValue();
    }

    @Override
    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup.setValue(toggleGroup);
    }

    @Override
    public ObjectProperty<ToggleGroup> toggleGroupProperty() {
        return toggleGroup;
    }

    @Override
    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public void setSelected(boolean selected) {
        System.out.println("setSelected");
        if (selected) {
            this.getStyleClass().add("selected");
        }
        else {
            this.getStyleClass().remove("selected");
        }
        this.selected.setValue(selected);
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public void update() {
        this.setCoordinates(gizmo.getX() * ONE_L_IN_PIXELS, gizmo.getY() * ONE_L_IN_PIXELS);
    }

    public void setCanFocus(boolean canFocus) {
        this.canFocus = canFocus;
    }
}
