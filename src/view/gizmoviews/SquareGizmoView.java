package view.gizmoviews;

import javafx.scene.shape.Rectangle;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class SquareGizmoView extends GizmoViewContainer{

    public SquareGizmoView(Gizmo squareGizmo) {
        super(squareGizmo);

        double side = ONE_L_IN_PIXELS;
        Rectangle square = new Rectangle(side, side);
        square.getStyleClass().add("squareGizmo");
        square.setMouseTransparent(true);

        this.setMaxSize(side, side);

        this.getChildren().add(square);
    }

    public SquareGizmoView() {
        this(null);
    }
}
