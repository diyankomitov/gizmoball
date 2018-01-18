package view;

import javafx.beans.NamedArg;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;


public class TriangularBumperView extends Group {
    private Polygon triangle;
    private Paint fill;

    public TriangularBumperView(@NamedArg("side") double side) {
        super();
        triangle = new Polygon(0.0, 0.0, 0.0, side, side, side);
        setFill(Color.RED);
        this.getChildren().add(triangle);

    }

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill = fill;
        triangle.setFill(fill);
    }
}
