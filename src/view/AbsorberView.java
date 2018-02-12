package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Constants;

public class AbsorberView extends GizmoView {
    public AbsorberView() {
        super();
        double side = Constants.ONE_L_IN_PIXELS;

        Rectangle absorber = new Rectangle(side, side);
        absorber.setFill(Color.PURPLE); //TODO put in css
        this.getChildren().add(absorber);
    }
}
