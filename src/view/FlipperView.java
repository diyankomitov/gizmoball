package view;

import javafx.beans.NamedArg;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import util.Constants;


import static view.FlipperOrientation.LEFT;

public class FlipperView extends Group{
    public FlipperView(@NamedArg("orientation") FlipperOrientation orientation) {
        super();

        double length = Constants.ONE_L_IN_PIXELS*2;
        double width = length/4;

        Rectangle flipper = new Rectangle(width, length);
        flipper.setArcHeight(width);
        flipper.setArcWidth(width);
        flipper.setFill(Color.ORANGE); //TODO put in css

        Rotate rotate = new Rotate();

        AnchorPane flipperBox = new AnchorPane();

        flipperBox.setPrefSize(length, length);

        if (orientation == LEFT) {
            AnchorPane.setLeftAnchor(flipper, 0.0);
        }
        else {
            AnchorPane.setRightAnchor(flipper, 0.0);
        }
        flipperBox.getChildren().add(flipper);

        this.getChildren().add(flipperBox);
    }
}
