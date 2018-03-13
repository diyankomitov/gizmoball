package view.gizmoviews;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class TriangleGizmoView extends GizmoViewContainer{

    public TriangleGizmoView(Gizmo triangleGizmo) {
        super(triangleGizmo);

        double side = ONE_L_IN_PIXELS;
        Polygon triangle = new Polygon(0.0, 0.0, side, 0.0, 0.0, side);
        triangle.setFill(Color.GREEN); //TODO put in css
        if (gizmo != null) {
            rotate(gizmo.getAngle());
        }

        this.getChildren().add(triangle);
    }

    public TriangleGizmoView() {
        this(null);
//        square.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }

    public void rotate(double angle){
        this.setRotate(angle);
    }

    @Override
    public void update() {
        super.update();
        rotate(gizmo.getAngle());
    }
}
