package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.*;

import static util.Constants.MILLIS_PER_FRAME;
import static util.Constants.ONE_L;
import static util.Constants.ONE_L_IN_PIXELS;
import static view.FlipperDirection.LEFT;
import static view.FlipperDirection.RIGHT;


public class CollisionPrototype extends Application {
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gizmoball");

        Pane pane = new Pane();

        pane.setPrefSize(ONE_L_IN_PIXELS*20, ONE_L_IN_PIXELS*20);


        GizmoballModel model = new GizmoballModel();

        SquareGizmo square = new SquareGizmo(12,14, ONE_L, "s1");
        TriangleGizmo triangle = new TriangleGizmo(4,15,ONE_L, "t1");
        CircleGizmo circle = new CircleGizmo(6,15,ONE_L, "c1");


        model.addGizmo(square);
        model.addGizmo(triangle);
        model.addGizmo(circle);

        GizmoView squareView = new SquareBumperView(square);
        GizmoView triangleView = new TriangularBumperView(triangle);
        GizmoView circleView = new CircularBumperView(circle);

        pane.getChildren().addAll(squareView, triangleView, circleView);
//        model.addBall(6.5,2,0,0);
        model.addBall(4.5, 2, 0, 0);
//        model.addBall(2.5, 2, 0, 0);


        BallView ballView = new BallView(model.getBall());
        pane.getChildren().add(ballView);

        Scene scene = new Scene(pane);

        Timeline timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> {
                            model.moveBall();
                        } //moves the ball
                ),
                new KeyFrame(
                        Duration.millis(MILLIS_PER_FRAME) //then waits 50 miliseconds before running again
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }
}
