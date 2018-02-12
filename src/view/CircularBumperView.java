package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Constants;

public class CircularBumperView extends GizmoView {

    public CircularBumperView() {
        super();

        double radius = Constants.ONE_L_IN_PIXELS / 2;

        Circle circle = new Circle(radius);
        circle.setFill(Color.DEEPSKYBLUE); //TODO put in css


        this.getChildren().add(circle);
    }
}
