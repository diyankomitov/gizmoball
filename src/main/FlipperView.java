package main;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import util.Observer;

import static main.Constants.ONE_L_IN_PIXELS;

public class FlipperView extends Group implements Observer{

    private final double x;
    private final double y;
    private final Flipper flipper;
    private final Rotate rotate;
    private double angle;

    public FlipperView(double x, double y, Flipper flipper) {
        this.x = x;
        this.y = y;
        this.flipper = flipper;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        this.setTranslateX(ONE_L_IN_PIXELS*x);
        this.setTranslateY(ONE_L_IN_PIXELS*y);
        rotate = new Rotate(0, ONE_L_IN_PIXELS/8, ONE_L_IN_PIXELS/8);
        this.getTransforms().add(rotate);
        flipper.subscribe(this);

    }

    @Override
    public void update() {
        angle = flipper.getAngle();
        rotate.setAngle(angle);
    }
}
