package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.*;
import view.*;

public class BuildModeController {
    private final Timeline timeline;

    public BorderPane rootPane;
    public BoardView board;
    public SquareBumperView squareButton;
    public CircularBumperView circleButton;
    public TriangularBumperView triangleButton;
    public FlipperView leftFlipperButton;
    public FlipperView rightFlipperButton;
    public Button addButton;
    public Button moveButton;
    public Button rotateButton;
    public ToolBar bumperBar;
    public Button deleteButton;


    public static final double ONE_L_IN_PIXELS = 40;

    private static int mode = 0;
    private GizmoballModel model;
    private GizmoType selectedType;
    private Node gizmoButton;
    private Pane gizmoButtonParent;

    public BuildModeController() {

        timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> model.moveBall() //moves the ball
                ),
                new KeyFrame(
                        Duration.millis(50) //then waits 50 miliseconds before running again
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called


    }

    public void onSquarePressed(MouseEvent mouseEvent) {
        selectedType = GizmoType.SQUARE;
        setGizmoButton(mouseEvent);
    }

    public void onCirclePressed(MouseEvent mouseEvent) {
        selectedType = GizmoType.CIRCLE;
        setGizmoButton(mouseEvent);
    }

    public void onTrianglePressed(MouseEvent mouseEvent) {
        selectedType = GizmoType.TRIANGLE;
        setGizmoButton(mouseEvent);
    }

    public void onLFlipperPressed(MouseEvent mouseEvent) {
        selectedType = GizmoType.LEFT_FLIPPER;
        setGizmoButton(mouseEvent);
    }

    public void onRFlipperPressed(MouseEvent mouseEvent) {
        selectedType = GizmoType.RIGHT_FLIPPER;
        setGizmoButton(mouseEvent);
    }

    private void setGizmoButton(MouseEvent mouseEvent) {
        gizmoButton = (Node) mouseEvent.getSource();
        gizmoButtonParent = (StackPane) gizmoButton.getParent();

        double bumperX = mouseEvent.getSceneX() - ONE_L_IN_PIXELS / 2;
        double bumperY = mouseEvent.getSceneY() - ONE_L_IN_PIXELS / 2;

        rootPane.getChildren().add(gizmoButton);

        translateBumper(bumperX, bumperY);

        gizmoButton.setMouseTransparent(true);
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).startFullDrag();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        double bumperX = mouseEvent.getSceneX() - ONE_L_IN_PIXELS / 2;
        double bumperY = mouseEvent.getSceneY() - ONE_L_IN_PIXELS / 2;
        translateBumper(bumperX, bumperY);
    }

    public void onButtonReleased(MouseEvent mouseEvent) {
        Bounds boardBounds = getBoundsInScene(board);
        Bounds bumperBounds = getBoundsInScene(gizmoButton);

        if (boardBounds.contains(bumperBounds)) {
            int x = board.getCurCol();
            int y = board.getCurRow();
//            board.add(gizmoButton, board.getCurCol(), board.getCurRow());
            model.addGizmo(x,y,selectedType); //TODO: check if exists already

            GizmoViewController controller = new GizmoViewController(this);
            GizmoView gizmoView;

            switch (selectedType) {
//                case CIRCLE:
//                    gizmoView = new CircularBumperView(ONE_L_IN_PIXELS, controller);
//                    break;
//                case SQUARE:
//                    gizmoView = new SquareBumperView(ONE_L_IN_PIXELS, controller);
//                    break;
//                case TRIANGLE:
//                    gizmoView = new TriangularBumperView(ONE_L_IN_PIXELS, controller);
//                    break;
//                default:
//                    gizmoView = null; //TODO: implement proper default
            }

//            controller.setGizmoView(gizmoView);
//            board.add(gizmoView, x,y);

        }

        gizmoButton.setMouseTransparent(false);
        gizmoButton.setTranslateX(0.0);
        gizmoButton.setTranslateY(0.0);
        gizmoButtonParent.getChildren().add(gizmoButton);
    }

    public void moveGizmo(GizmoView gizmoView) {
        int fromX, fromY, toX, toY;

        fromX = GridPane.getColumnIndex(gizmoView);
        fromY = GridPane.getRowIndex(gizmoView);
        toX = board.getCurCol();
        toY = board.getCurRow();


        GridPane.setColumnIndex(gizmoView,toX);
        GridPane.setRowIndex(gizmoView,toY);
        model.moveGizmo(fromX, fromY, toX, toY);
    }

    public void removeGizmo(GizmoView gizmoView) {
        int x = GridPane.getColumnIndex(gizmoView);
        int y = GridPane.getRowIndex(gizmoView);
        model.removeGizmo(x,y);
    }

    private Bounds getBoundsInScene(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    private void translateBumper(double x, double y) {
        gizmoButton.setTranslateX(x);
        gizmoButton.setTranslateY(y);
    }

    public void setAdd(ActionEvent actionEvent) {
        mode = 0;
        bumperBar.setVisible(true);
    }

    public void setMove(ActionEvent actionEvent) {
        mode = 1;
        bumperBar.setVisible(false);
    }

    public void setRotate(ActionEvent actionEvent) {
        mode = 2;
        bumperBar.setVisible(false);
    }

    public void setDelete(ActionEvent actionEvent) {
        mode = 3;
        bumperBar.setVisible(false);
    }

    public void start(ActionEvent actionEvent) {
        timeline.play();
    }

    public void setModel(GizmoballModel model) {
        this.model = model;
//        board.setModel(model);
    }

    public int getBuildMode() {
        return mode;
    }

    public void switchMode(ActionEvent actionEvent) {

    }






//    private void setBumperMovement(Node newBumper) {
//        final double[] offsetX = {0};
//        final double[] offsetY = {0};
//
//        newBumper.setOnMousePressed(event -> {
//            if (mode == 1) {
//                gizmoButton = newBumper;
//                offsetX[0] = getBoundsInScene(newBumper).getMinX();
//                offsetY[0] = getBoundsInScene(newBumper).getMinY();
//                System.out.println(offsetX[0] + " " + offsetY[0]);
//                newBumper.setMouseTransparent(true);
//            }
//            if (mode == 2) {
//                newBumper.setRotate(newBumper.getRotate()+90);
//            }
//            if (mode == 3) {
//                board.getChildren().remove(newBumper);
//            }
//        });
//        newBumper.setOnDragDetected(event -> {
//            if (mode == 1) {
//                onDragDetected(event);
//            }
//        });
//        newBumper.setOnMouseDragged(event -> {
//            if (mode == 1) {
//                translateBumper(event.getSceneX()-offsetX[0], event.getSceneY()-offsetY[0]);
//            }
//        });
//        newBumper.setOnMouseReleased(event -> {
//            if (mode == 1) {
//                onBumperReleased(event);
//                newBumper.setMouseTransparent(false);
//            }
//        });
//
//    }

}
