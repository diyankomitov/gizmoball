package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Gizmo;
import util.Constants;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;

public class CircularBumperView extends GizmoView implements Observer {

    private final double x;
    private final double y;
    private final Gizmo circle;

    public CircularBumperView(Gizmo circle) {
        super();
        this.x = circle.getXCoord() * ONE_L_IN_PIXELS;
        this.y = circle.getYCoord() * ONE_L_IN_PIXELS;
        this.circle = circle;
        Circle physCircle = new Circle();

        double radius = ONE_L_IN_PIXELS / 2;

        Circle circle = new Circle(radius);
        circle.setFill(Color.DEEPSKYBLUE); //TODO put in css


        this.getChildren().add(circle);
    }

    @Override
    public void update() {

    }
}
