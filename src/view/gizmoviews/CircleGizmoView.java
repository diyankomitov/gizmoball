package view.gizmoviews;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class CircleGizmoView extends GizmoViewContainer {

    private final Circle circle;

    public CircleGizmoView(Gizmo circleGizmo) {
        super(circleGizmo);

        double radius = ONE_L_IN_PIXELS/2;
        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        circle.setFill(Color.DEEPSKYBLUE); //TODO: put in css

        this.getChildren().add(circle);
    }

    public CircleGizmoView() {
        this(null);
//        circle.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }
}
