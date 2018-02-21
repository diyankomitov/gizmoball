package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.board.gizmos.CircleGizmo;
import model.board.gizmos.SquareGizmo;
import model.board.gizmos.TriangleGizmo;
import view.*;

import static util.Constants.*;


public class CollisionPrototype extends Application {
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gizmoball");

        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black");

        pane.setPrefSize(ONE_L_IN_PIXELS*BOARD_WIDTH, ONE_L_IN_PIXELS*BOARD_WIDTH);


        GizmoballModel model = new GizmoballModel();

        SquareGizmo square1 = new SquareGizmo(12,14, ONE_L, "s1");
        SquareGizmo square2 = new SquareGizmo(5,5, ONE_L, "s2");
        SquareGizmo square3 = new SquareGizmo(2,16, ONE_L, "s3");
        SquareGizmo square4 = new SquareGizmo(15,5, ONE_L, "s4");
        TriangleGizmo triangle1 = new TriangleGizmo(4,7,ONE_L, "t1");
        triangle1.rotate();
        triangle1.rotate();
        TriangleGizmo triangle2 = new TriangleGizmo(2,4,ONE_L, "t2");
        triangle2.rotate();
        TriangleGizmo triangle3 = new TriangleGizmo(6,15,ONE_L, "t3");
        TriangleGizmo triangle4 = new TriangleGizmo(17,11,ONE_L, "t4");
        triangle4.rotate();
        triangle4.rotate();
        triangle4.rotate();
        CircleGizmo circle1 = new CircleGizmo(3,15,ONE_L, "c1");
        CircleGizmo circle2 = new CircleGizmo(6,14,ONE_L, "c2");
        CircleGizmo circle3 = new CircleGizmo(11,2,ONE_L, "c3");
        CircleGizmo circle4 = new CircleGizmo(17,5,ONE_L, "c4");


        model.addGizmo(square1);
        model.addGizmo(square2);
        model.addGizmo(square3);
        model.addGizmo(square4);
        model.addGizmo(triangle1);
        model.addGizmo(triangle2);
        model.addGizmo(triangle3);
        model.addGizmo(triangle4);
        model.addGizmo(circle1);
        model.addGizmo(circle2);
        model.addGizmo(circle3);
        model.addGizmo(circle4);

        GizmoView squareView1 = new SquareBumperView(square1);
        GizmoView squareView2 = new SquareBumperView(square2);
        GizmoView squareView3 = new SquareBumperView(square3);
        GizmoView squareView4 = new SquareBumperView(square4);
        GizmoView triangleView1 = new TriangularBumperView(triangle1);
        GizmoView triangleView2 = new TriangularBumperView(triangle2);
        GizmoView triangleView3 = new TriangularBumperView(triangle3);
        GizmoView triangleView4 = new TriangularBumperView(triangle4);
        GizmoView circleView1 = new CircularBumperView(circle1);
        GizmoView circleView2 = new CircularBumperView(circle2);
        GizmoView circleView3 = new CircularBumperView(circle3);
        GizmoView circleView4 = new CircularBumperView(circle4);

        pane.getChildren().addAll(squareView1, squareView2, squareView3, squareView4, triangleView1, triangleView2, triangleView3, triangleView4, circleView1, circleView2, circleView3, circleView4);

//        model.addBall(6.5,2,0,0);
        model.addBall(2, 2, 3, 2, "B1");
//        model.addBall(2, 3, 4, 0, "B2");
//        model.addBall(2, 4, 4, 0, "B3");
//        model.addBall(2, 5, 4, 0, "B4");
//        model.addBall(2, 6, 4, 0, "B5");
//        model.addBall(2, 7, 4, 0, "B6");
//        model.addBall(18, 2, -4, 0, "B7");
//        model.addBall(18, 3, -4, 0, "B8");
//        model.addBall(18, 4, -4, 0, "B9");
//        model.addBall(18, 5, -4, 0, "B10");
//        model.addBall(18, 6, -4, 0, "B11");
//        model.addBall(18, 7, -4, 0, "B12");


        model.getBalls().forEach(ball -> {
            BallView ballView = new BallView(ball);
            pane.getChildren().add(ballView);
        });

        Scene scene = new Scene(pane);
        Timeline timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> {
                            model.moveBalls();
                        } //moves the ball
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }
}
