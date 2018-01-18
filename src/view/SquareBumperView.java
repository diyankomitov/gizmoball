package view;

import javafx.beans.NamedArg;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class SquareBumperView extends Group {
    private Rectangle square;
    private Paint fill;

    public SquareBumperView(@NamedArg("side") double side) {
        super();
        square = new Rectangle(side, side);
        setFill(Color.YELLOW);
        this.getChildren().add(square);
    }

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill = fill;
        square.setFill(fill);
    }
}
