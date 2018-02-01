package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import static main.Constants.ONE_L;
import static main.Constants.ONE_L_IN_PIXELS;
import static main.FlipperDirection.LEFT;
import static main.FlipperDirection.RIGHT;


public class  FlipperPrototype extends Application {
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gizmoball");

        Pane pane = new Pane();

        pane.setPrefSize(ONE_L_IN_PIXELS*10, ONE_L_IN_PIXELS*10);


        Flipper flipperL = new Flipper(ONE_L*4, ONE_L*5, 0, LEFT );
        Flipper flipperR = new Flipper(ONE_L*6, ONE_L*5, 0, RIGHT );

        FlipperView flipperViewL = new FlipperView(4,5, flipperL);
        FlipperView flipperViewR = new FlipperView(6,5, flipperR);

        pane.getChildren().add(flipperViewL);
        pane.getChildren().add(flipperViewR);

       // Rotate rotate = new Rotate(0, ONE_L_IN_PIXELS/8, ONE_L_IN_PIXELS/8);
        //rectangleR.getTransforms().add(rotate);

        Scene scene = new Scene(pane);

        Timeline timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> {
                            flipperL.rotate();
                            flipperR.rotate();
                        } //moves the ball
                ),
                new KeyFrame(
                        Duration.millis(50) //then waits 50 miliseconds before running again
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called


        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    System.out.println("pressleft");
                    flipperL.setTriggered(true);
                    break;
                case RIGHT:
                    System.out.println("pressright");
                    flipperR.setTriggered(true);
                    break;
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    flipperL.setTriggered(false);
                    break;
                case RIGHT:
                    flipperR.setTriggered(false);
                    break;
            }
            System.out.println("release");
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }
}