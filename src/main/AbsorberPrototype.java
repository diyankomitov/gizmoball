package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.board.gizmos.AbsorberGizmo;
import model.GizmoballModel;
import view.AbsorberView;
import view.BallView;

import static util.Constants.MILLIS_PER_FRAME;
import static util.Constants.ONE_L_IN_PIXELS;


public class AbsorberPrototype extends Application {
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gizmoball");

        Pane pane = new Pane();

        pane.setPrefSize(ONE_L_IN_PIXELS*20, ONE_L_IN_PIXELS*20);

        //AbsorberGizmo absorber = new AbsorberGizmo(0,19, 20,20, "A");
        //AbsorberView absorberView = new AbsorberView(absorber);

        GizmoballModel model = new GizmoballModel();
//        model.addGizmo(absorber);
        model.addBall(2,1,0,0, "B");


        model.getBalls().forEach(ball -> {
            BallView ballView = new BallView(ball);
            pane.getChildren().add(ballView);
        });

       // pane.getChildren().add(/*absorberView*/);

        Timeline timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> {
                            model.moveBalls();
                        } //moves the ball
                ),
                new KeyFrame(
                        Duration.millis(MILLIS_PER_FRAME) //then waits 50 miliseconds before running again
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called


        Scene scene = new Scene(pane);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
//                    absorber.shootBall();
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }
}