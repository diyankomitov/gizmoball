package view;

import javafx.beans.NamedArg;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FlipperView extends Group{
    private final FlipperOrientation orientation;
    private final Region flipper;
    //    private final Rectangle flipper;
    private Paint fill;


    public FlipperView(@NamedArg("length") double length, @NamedArg("orientation") FlipperOrientation orientation) {
        super();
        this.orientation = orientation;
//        flipper = new Rectangle(length, length/3);
        flipper = new Region();
        flipper.setPrefSize(length, length/3);
        String style;
        if (orientation == FlipperOrientation.LEFT) {
            style = "-fx-background-radius: 0 15 15 0";
        }
        else {
            style = "-fx-background-radius: 15 0 0 15";
        }
        flipper.setStyle("-fx-background-color: green; " + style);

        Pane flipperBox = new Pane();
        flipperBox.setPrefSize(length, length);
        flipperBox.getChildren().add(flipper);
        flipper.setTranslateY(length-(length/3));


        this.getChildren().add(flipperBox);
    }

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill = fill;
//        flipper.setFill(fill);
    }

}
