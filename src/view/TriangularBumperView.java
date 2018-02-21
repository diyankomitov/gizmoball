package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.board.gizmos.TriangleGizmo;
import util.Constants;


public class TriangularBumperView extends GizmoView {

    public TriangularBumperView(TriangleGizmo triangleGizmo) {
        super(triangleGizmo);


        double side = Constants.ONE_L_IN_PIXELS;

        Polygon triangle = new Polygon(0.0, 0.0, 0.0, side, side, side);
        triangle.setFill(Color.GREEN); //TODO put in css
        rotate(triangleGizmo.getAngle());

        this.getChildren().add(triangle);
    }

    public TriangularBumperView() {
        super();
        double side = Constants.ONE_L_IN_PIXELS;

        Polygon triangle = new Polygon(0.0, 0.0, 0.0, side, side, side);
        triangle.setFill(Color.GREEN); //TODO put in css

        this.getChildren().add(triangle);
    }

    public void rotate(double angle){
        this.setRotate(angle);
    }

}
