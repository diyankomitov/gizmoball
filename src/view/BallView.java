package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Ball;
import util.Constants;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;


public class BallView extends Group implements Observer{

    private double x;
    private double y;
    private final Ball ballModel;

    public BallView(Ball ballModel) {
        super();
        this.x = ballModel.getX() * ONE_L_IN_PIXELS;
        this.y = ballModel.getY() * ONE_L_IN_PIXELS;
        this.ballModel = ballModel;
        double radius = ONE_L_IN_PIXELS/4;
        Circle ball = new Circle(radius);
        ball.setFill(Color.CYAN); //TODO put in css
        this.getChildren().add(ball);

        this.setTranslateX(x);
        this.setTranslateY(y);

        ballModel.subscribe(this);
    }

    @Override
    public void update() {
        this.setTranslateX(ballModel.getX() * ONE_L_IN_PIXELS);
        this.setTranslateY(ballModel.getY() * ONE_L_IN_PIXELS);
    }
}
