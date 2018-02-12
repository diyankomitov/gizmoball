package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Constants;


public class BallView extends Group{

    public BallView() {
        double radius = Constants.ONE_L_IN_PIXELS/4;
        Circle ball = new Circle(radius);
        ball.setFill(Color.CYAN); //TODO put in css
        this.getChildren().add(ball);
    }
}
