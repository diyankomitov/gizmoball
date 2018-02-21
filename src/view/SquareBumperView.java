package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.board.gizmos.SquareGizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class SquareBumperView extends GizmoView {

    private final Rectangle square;
    private boolean isToggle;

    public SquareBumperView(SquareGizmo squareGizmo) {
        super(squareGizmo);
        double side = ONE_L_IN_PIXELS;
        isToggle = false;

        square = new Rectangle(side, side);
        square.setFill(Color.RED); //TODO: put in css


        this.getChildren().add(square);
    }

    public SquareBumperView() {
        double side = ONE_L_IN_PIXELS;
        isToggle = false;

        square = new Rectangle(side, side);
        square.setFill(Color.RED); //TODO: put in css
        square.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");

        this.getChildren().add(square);
    }

    public void toggle() {
        if (!isToggle) {
            square.setFill(Color.DARKRED);
        }
        else {
            square.setFill(Color.RED); //TODO: put in css
        }

        isToggle = !isToggle;
    }
}
