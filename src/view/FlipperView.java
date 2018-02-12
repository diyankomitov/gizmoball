package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.Flipper;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperView extends Group implements Observer{

    private final double x;
    private final double y;
    private final Flipper flipper;
    private final Rotate rotate;
    private double angle;

    public FlipperView(Flipper flipper) {
        this.x = flipper.getXpos() * ONE_L_IN_PIXELS;
        this.y = flipper.getYpos() * ONE_L_IN_PIXELS;
        this.flipper = flipper;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.ORANGE);

        this.setTranslateX(x);
        this.setTranslateY(y);

        rotate = new Rotate(0, ONE_L_IN_PIXELS/4, ONE_L_IN_PIXELS/4);
        this.getTransforms().add(rotate);

        flipper.subscribe(this);
    }

    public FlipperView() {
        this.x = 0;
        this.y = 0;
        this.flipper = null;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.ORANGE);

        rotate = null;
    }

    @Override
    public void update() {
        angle = flipper.getAngle();
        rotate.setAngle(angle);
    }
}
