package view;

import javafx.beans.NamedArg;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircularBumperView extends Group {
    private Circle circle;
    private Paint fill;

    public CircularBumperView(@NamedArg("diameter") double diameter) {
        super();
        circle = new Circle(diameter/2);
        setFill(Color.BLUE);
        this.getChildren().add(circle);
    }

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill = fill;
        circle.setFill(fill);
    }
}
