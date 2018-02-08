package main;

import controller.GizmoballController;
import controller.LoadHandler;
import controller.SaveHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class  Main extends Application {
    public double width = 0;
    private int direction;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Gizmoball");

        FXMLLoader mainFXML = new FXMLLoader(getClass().getResource("../view/fxml/GizmoballView.fxml"));


        Parent root = mainFXML.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/css/styles.css");

        GizmoballController gizmoballController = mainFXML.getController();
        gizmoballController.setSaveHandler(new SaveHandler(primaryStage));
        gizmoballController.setLoadHandler(new LoadHandler(primaryStage, gizmoballController.getBoard()));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}


//        timeline.play();


//    public void rotate(Rotate rotation) {
//        if (direction == -1) {
//
//            double angle = Math.min(rotation.getAngle() + 54, 90);
//            rotation.setAngle(angle);
//        }
//        else if (direction == 1) {
//            double angle = Math.max(rotation.getAngle() - 54, -90);
//            rotation.setAngle(angle);
//
//        }
//        else if (direction == 0) {
//            if (rotation.getAngle() > 0) {
//                double angle = Math.max(rotation.getAngle() - 54, 0);
//                rotation.setAngle(angle);
//            }
//            else {
//                double angle = Math.min(rotation.getAngle() + 54, 0);
//                rotation.setAngle(angle);
//            }
//        }
//    }


//        BorderPane pane = new BorderPane();
//        pane.setPrefSize(500,500);
//        Rectangle rectangle = new Rectangle(25, 100);
//        rectangle.setArcWidth(25);
//        rectangle.setArcHeight(25);
//        pane.setCenter(rectangle);
//
//
//        Rotate rotate = new Rotate(0, 25/2,25/2);
//        rectangle.getTransforms().add(rotate);
//
//        Scene scene = new Scene(pane);
//
////        AnimationTimer timer = new AnimationTimer() {
////            @Override
////            public void handle(long now) {
////               scene.setOnKeyPressed(e -> {
////                    if (e.getCode() == KeyCode.LEFT) {
////                        rotate.setAngle(rotate.getAngle() + 18);
////                    } else if (e.getCode() == KeyCode.RIGHT){
////                        rotate.setAngle(rotate.getAngle() - 18);
////                    }
////                });
////            }
////        };
//
//        Timeline timeline = new Timeline(
//                new KeyFrame(   //keyframes allow for something to happen at a given time
//                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
//                        actionEvent -> rotate(rotate) //moves the ball
//                ),
//                new KeyFrame(
//                        Duration.millis(50) //then waits 50 miliseconds before running again
//                )
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called
//
////        final Timeline timeline = new Timeline();
////        timeline.setCycleCount(2);
////        timeline.setAutoReverse(true);
////        timeline.getKeyFrames().add(
////                new KeyFrame(Duration.millis(200),
////                new KeyValue(rotate.angleProperty(), 90)));
//
//        scene.setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case LEFT:
//                    direction = -1;
//                    break;
//                case RIGHT:
//                    direction = 1;
//                    break;
//            }
//        });
//        scene.setOnKeyReleased(event -> {
//            switch (event.getCode()) {
//                case LEFT: case RIGHT:
//                    direction = 0;
//                    break;
//            }
//        });
//
////        Timeline timeline = new Timeline(
////                new KeyFrame(   //keyframes allow for something to happen at a given time
////                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
////                        actionEvent -> model.moveBall() //moves the ball
////                ),
////                new KeyFrame(
////                        Duration.millis(50) //then waits 50 miliseconds before running again
////                )
////        );
////        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called







//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Main.fxml"));
//
//        Parent root = (Parent)fxmlLoader.load();
//        MainController controller = fxmlLoader.<MainController>getController();
//        controller.setUser(user_id);
//        Scene scene = new Scene(root);

//        FXMLLoader buildFXML = new FXMLLoader(getClass().getResource("../view/fxml/BuildView.fxml"));
//        FXMLLoader playFXML = new FXMLLoader(getClass().getResource("../view/fxml/PlayView.fxml"));
//
//
//        BuildController buildController = new BuildController();
//        PlayController playController = new PlayController();
//        GizmoballController gizmoballController = new GizmoballController(buildController, playController);
//
//
//        buildFXML.setController(buildController);
//        playFXML.setController(playController);
//        mainFXML.setController(gizmoballController);

//
//        BuildModeController controller = fxmlLoader.getController();
//        GizmoballModel model = new GizmoballModel();
//        controller.setModel(model);

//        StackPane root = new StackPane();

//        BorderPane build = new BorderPane();
//        build.setStyle("-fx-background-color: red;");
//        build.setPrefSize(500, 500);
//        Button switchToPlay = new Button("Switch to play");
//        build.setCenter(switchToPlay);
//
//        BorderPane play = new BorderPane();
//        play.setStyle("-fx-background-color: blue");
//        play.setPrefSize(500, 500);
//        Button switchToBuild = new Button("Switch to build");
//        play.setCenter(switchToBuild);
//
//        switchToPlay.setOnAction(new SwitchModeHandler(build, play, RIGHT));
//        switchToBuild.setOnAction(new SwitchModeHandler(play, build, LEFT));
//
//        root.getChildren().addAll(play, build);