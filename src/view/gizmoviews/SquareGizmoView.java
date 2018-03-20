package view.gizmoviews;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class SquareGizmoView extends GizmoViewContainer{

    private final Rectangle square;

    public SquareGizmoView(Gizmo squareGizmo) {
        super(squareGizmo);

        double side = ONE_L_IN_PIXELS;
        square = new Rectangle(side, side);
        square.getStyleClass().add("squareGizmo");
        square.setMouseTransparent(true);

        this.setMaxSize(side, side);

        this.getChildren().add(square);
    }

    public SquareGizmoView() {
        this(null);
    }
}
