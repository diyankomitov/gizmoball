package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.board.Ball;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;


public class BallView extends Group implements Observer{

    private Circle ball;
    private double x;
    private double y;
    private final Ball ballModel;
    private double hue;

    public BallView(Ball ballModel) {
        super();
        this.x = ballModel.getX() * ONE_L_IN_PIXELS;
        this.y = ballModel.getY() * ONE_L_IN_PIXELS;
        this.ballModel = ballModel;
        double radius = ONE_L_IN_PIXELS/4;
        ball = new Circle(radius);
        this.getChildren().add(ball);
        ball.getStyleClass().add("ball");
        this.setTranslateX(x);
        this.setTranslateY(y);
        ballModel.subscribe(this);
    }

    public BallView() {
        this.x = 0;
        this.y = 0;
        this.ballModel = null;
        double radius = ONE_L_IN_PIXELS/4;
        ball = new Circle(radius);
        this.getChildren().add(ball);
        ball.getStyleClass().add("ball");
    }

    @Override
    public void update() {
        this.setTranslateX(ballModel.getX() * ONE_L_IN_PIXELS);
        this.setTranslateY(ballModel.getY() * ONE_L_IN_PIXELS);
        if (hue == 360) {
            hue = 0;
        }
//        ball.setFill(Color.hsb(hue,1.0,1.0));
        hue++;
    }
}
