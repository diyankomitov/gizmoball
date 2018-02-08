package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import main.Flipper;
import model.Absorber;
import util.Constants;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;

public class AbsorberView extends GizmoView implements Observer{
    private final double x;
    private final double y;
    private final Absorber absorberModel;

    public AbsorberView(Absorber absorberModel) {
        super();
        this.x = absorberModel.getXCoord() * ONE_L_IN_PIXELS;
        this.y = absorberModel.getYCoord() * ONE_L_IN_PIXELS;
        this.absorberModel = absorberModel;
        Rectangle rectangle = new Rectangle(absorberModel.getWidth() * ONE_L_IN_PIXELS, absorberModel.getHeight() * ONE_L_IN_PIXELS);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.PURPLE); //TODO: move elsewhere

        this.setTranslateX(x);
        this.setTranslateY(y);

        absorberModel.subscribe(this);
    }

    @Override
    public void update() {

    }
}