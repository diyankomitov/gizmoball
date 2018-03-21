package view.gizmoviews;

import javafx.scene.shape.Circle;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class CircleGizmoView extends GizmoViewContainer {

    public CircleGizmoView(Gizmo circleGizmo) {
        super(circleGizmo);

        double radius = ONE_L_IN_PIXELS/2;
        Circle circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        circle.getStyleClass().add("circleGizmo");
        circle.setMouseTransparent(true);
        this.getChildren().add(circle);

        this.setMaxSize(radius*2, radius*2);
//        this.setEffect(GlobalLighting.get());
    }

    public CircleGizmoView() {
        this(null);
//        circle.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }
}
