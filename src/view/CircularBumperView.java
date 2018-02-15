package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.CircleGizmo;
import util.Constants;

import static util.Constants.ONE_L_IN_PIXELS;

public class CircularBumperView extends GizmoView {

    public CircularBumperView(CircleGizmo circleGizmo) {
        super(circleGizmo);


        double radius = ONE_L_IN_PIXELS / 2;

        Circle circle = new Circle(radius);
        this.setTranslateX(this.getTranslateX() + radius);
        this.setTranslateY(this.getTranslateY() + radius);
        circle.setFill(Color.DEEPSKYBLUE); //TODO put in css


        this.getChildren().add(circle);
    }

    public CircularBumperView() {
        super();
        double radius = ONE_L_IN_PIXELS / 2;

        Circle circle = new Circle(radius);
        circle.setFill(Color.DEEPSKYBLUE); //TODO put in css

        this.getChildren().add(circle);
    }
}
