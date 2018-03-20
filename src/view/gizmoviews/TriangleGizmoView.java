package view.gizmoviews;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class TriangleGizmoView extends GizmoViewContainer{

    private final Polygon triangle;

    public TriangleGizmoView(Gizmo triangleGizmo) {
        super(triangleGizmo);

        double side = ONE_L_IN_PIXELS;
        triangle = new Polygon(0.0, 0.0, side, 0.0, 0.0, side);
        triangle.getStyleClass().add("triangleGizmo");
        triangle.setMouseTransparent(true);
        if (gizmo != null) {
            rotate(gizmo.getAngle());
        }

        this.getChildren().add(triangle);
    }

    public TriangleGizmoView() {
        this(null);
    }

    public void rotate(double angle){
        triangle.setRotate(angle);
    }

    @Override
    public void update() {
        super.update();
        if (triangle != null)
        rotate(gizmo.getAngle());
    }
}
