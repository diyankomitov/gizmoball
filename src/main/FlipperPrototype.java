package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class  FlipperPrototype extends Application {
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gizmoball");

        Pane pane = new Pane();
//
//        pane.setPrefSize(ONE_L_IN_PIXELS*20, ONE_L_IN_PIXELS*20);
//
//        FlipperGizmo flipperL = new FlipperGizmo(3, 15, 0, LEFT, "LF315" );
//        FlipperGizmo flipperR = new FlipperGizmo(5, 15, 0, RIGHT, "RF515" );
//
//        FlipperView flipperViewL = new FlipperView(flipperL);
//        FlipperView flipperViewR = new FlipperView(flipperR);
//
//
//        pane.getChildren().add(flipperViewL);
//        pane.getChildren().add(flipperViewR);
//
//        GizmoballModel model = new GizmoballModel();
//        model.addGizmo(flipperL);
//        model.addGizmo(flipperR);
//        model.addBall(4,2,0,0, "B");
//
//
//        model.getBalls().forEach(ball -> {
//            BallView ballView = new BallView(ball);
//            pane.getChildren().add(ballView);
//        });
////
////        pane.getChildren().addAll(flipperViewL.getPoint(), flipperViewL.getPoint2(), flipperViewL.getPoint3(), flipperViewL.getPoint4());
////        pane.getChildren().addAll(flipperViewR.getPoint(), flipperViewR.getPoint2(), flipperViewR.getPoint3(), flipperViewR.getPoint4());
//
//        // Rotate rotate = new Rotate(0, ONE_L_IN_PIXELS/8, ONE_L_IN_PIXELS/8);
//        //rectangleR.getTransforms().addGizmo(rotate);
//
        Scene scene = new Scene(pane);
//
//        Timeline timeline = new Timeline(
//                new KeyFrame(   //keyframes allow for something to happen at a given time
//                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
//                        actionEvent -> {
//                            flipperL.rotate();
//                            flipperR.rotate();
////                            model.moveBalls();
//                        } //moves the ball
//                ),
//                new KeyFrame(
//                        Duration.millis(MILLIS_PER_FRAME) //then waits 50 miliseconds before running again
//                )
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called


        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    System.out.println(event.getEventType() + " : " + event.getCode());
                    break;
                case RIGHT:
                    System.out.println(event.getEventType() + " : " + event.getCode());
                    break;
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    System.out.println(event.getEventType() + " : " + event.getCode());
                    break;
                case RIGHT:
                    System.out.println(event.getEventType() + " : " + event.getCode());
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
//        timeline.play();
    }
}
