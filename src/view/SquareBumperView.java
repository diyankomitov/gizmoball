package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Constants;


public class SquareBumperView extends GizmoView {

    private final Rectangle square;
    private boolean isToggle;

    public SquareBumperView() {
        super();
        double side = Constants.ONE_L_IN_PIXELS;
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
