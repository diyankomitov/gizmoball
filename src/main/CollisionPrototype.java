package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.board.BoardObjectType;
import model.board.gizmos.CircleGizmo;
import model.board.gizmos.SquareGizmo;
import model.board.gizmos.TriangleGizmo;
import view.*;

import static model.board.BoardObjectType.*;
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

        model.addGizmo(12,14, "s1" , SQUARE);
        model.addGizmo(5,5, "s2" , SQUARE);
        model.addGizmo(2,16, "s3" , SQUARE);
        model.addGizmo(15,5, "s4" , SQUARE);
        model.addGizmo(4,7, "t1" , TRIANGLE);
        model.addGizmo(2,4, "t2" , TRIANGLE);
        model.addGizmo(6,15, "t3" , TRIANGLE);
        model.addGizmo(17,11, "t4" , TRIANGLE);
        model.addGizmo(3,15, "c1" , CIRCLE);
        model.addGizmo(6,14, "c2" , CIRCLE);
        model.addGizmo(11,2, "c3" , CIRCLE);
        model.addGizmo(17,15, "c4" , CIRCLE);

        model.rotateGizmo("t1");
        model.rotateGizmo("t1");
        model.rotateGizmo("t2");
        model.rotateGizmo("t4");
        model.rotateGizmo("t4");
        model.rotateGizmo("t4");

        GizmoView squareView1 = new SquareBumperView((SquareGizmo) model.getGizmo("s1"));
        GizmoView squareView2 = new SquareBumperView((SquareGizmo) model.getGizmo("s2"));
        GizmoView squareView3 = new SquareBumperView((SquareGizmo) model.getGizmo("s3"));
        GizmoView squareView4 = new SquareBumperView((SquareGizmo) model.getGizmo("s4"));
        GizmoView triangleView1 = new TriangularBumperView((TriangleGizmo) model.getGizmo("t1"));
        GizmoView triangleView2 = new TriangularBumperView((TriangleGizmo) model.getGizmo("t2"));
        GizmoView triangleView3 = new TriangularBumperView((TriangleGizmo) model.getGizmo("t3"));
        GizmoView triangleView4 = new TriangularBumperView((TriangleGizmo) model.getGizmo("t4"));
        GizmoView circleView1 = new CircularBumperView((CircleGizmo) model.getGizmo("c1"));
        GizmoView circleView2 = new CircularBumperView((CircleGizmo) model.getGizmo("c2"));
        GizmoView circleView3 = new CircularBumperView((CircleGizmo) model.getGizmo("c3"));
        GizmoView circleView4 = new CircularBumperView((CircleGizmo) model.getGizmo("c4"));

        pane.getChildren().addAll(squareView1, squareView2, squareView3, squareView4, triangleView1, triangleView2, triangleView3, triangleView4, circleView1, circleView2, circleView3, circleView4);

//        model.addBall(6.5,2,0,0);
        model.addBall(2, 2, 50, 50, "B1");
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

        model.setGravity(0);
        model.setFriction(0,0);

        Scene scene = new Scene(pane);

        Timeline timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),
                        actionEvent -> {
                            model.moveBalls();
                        } //moves the ball
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

//        scene.setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case SPACE:
//                    model.moveBalls();
//                    break;
//            }
//        });
        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }
}
