package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.TriangleGizmo;
import util.Constants;

import static util.Constants.ONE_L_IN_PIXELS;


public class TriangularBumperView extends GizmoView {

    public TriangularBumperView(TriangleGizmo triangleGizmo) {
        super(triangleGizmo);


        double side = Constants.ONE_L_IN_PIXELS;

        Polygon triangle = new Polygon(0.0, 0.0, 0.0, side, side, side);
        triangle.setFill(Color.GREEN); //TODO put in css

        this.getChildren().add(triangle);
    }
}
