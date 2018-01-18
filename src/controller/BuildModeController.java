package controller;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import view.*;

import javax.xml.crypto.Data;

public class BuildModeController {
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

    private Node bumper;
    private int mode = 0;
    public static final DataFormat GIZMO_FORMAT = new DataFormat("gizmo");

    public BuildModeController() {
    }

    private Bounds getBoundsInScene(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    private void translateBumper(double x, double y) {
        bumper.setTranslateX(x);
        bumper.setTranslateY(y);
    }

    private void setBumperMovement(Node newBumper) {
        final double[] offsetX = {0};
        final double[] offsetY = {0};

        newBumper.setOnMousePressed(event -> {
            if (mode == 1) {
                bumper = newBumper;
                offsetX[0] = getBoundsInScene(newBumper).getMinX();
                offsetY[0] = getBoundsInScene(newBumper).getMinY();
                System.out.println(offsetX[0] + " " + offsetY[0]);
                newBumper.setMouseTransparent(true);
            }
            if (mode == 2) {
                newBumper.setRotate(newBumper.getRotate()+90);
            }
            if (mode == 3) {
                board.getChildren().remove(newBumper);
            }

        });
        newBumper.setOnDragDetected(event -> {
            if (mode == 1) {
                onDragDetected(event);
            }
        });
        newBumper.setOnMouseDragged(event -> {
            if (mode == 1) {
                translateBumper(event.getSceneX()-offsetX[0], event.getSceneY()-offsetY[0]);
            }
        });
        newBumper.setOnMouseReleased(event -> {
            if (mode == 1) {
                onBumperReleased(event);
                newBumper.setMouseTransparent(false);
            }
        });

    }

    private void addBumper(MouseEvent mouseEvent) {
        rootPane.getChildren().add(bumper);
        bumper.setMouseTransparent(true);
        setBumperMovement(bumper);
        translateBumper(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    public void onSquarePressed(MouseEvent mouseEvent) {
        bumper = new SquareBumperView(40.0);
        addBumper(mouseEvent);
    }

    public void onCirclePressed(MouseEvent mouseEvent) {
        bumper = new CircularBumperView(40.0);
        addBumper(mouseEvent);
    }

    public void onTrianglePressed(MouseEvent mouseEvent) {
        bumper = new TriangularBumperView(40.0);
        addBumper(mouseEvent);
    }

    public void onLFlipperPressed(MouseEvent mouseEvent) {
        bumper = new FlipperView(40.0, FlipperOrientation.LEFT);
        addBumper(mouseEvent);
    }

    public void onRFlipperPressed(MouseEvent mouseEvent) {
        bumper = new FlipperView(40.0, FlipperOrientation.RIGHT);
        addBumper(mouseEvent);
    }

    public void onDragDetected(MouseEvent mouseEvent) {
//        System.out.println(mouseEvent.getEventType().toString());
//        System.out.println(mouseEvent.getEventType().toString());
        ((Node)mouseEvent.getSource()).startFullDrag();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        translateBumper(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    public void onButtonReleased(MouseEvent mouseEvent) {
        Bounds boardBounds = getBoundsInScene(board);
        Bounds bumperBounds = getBoundsInScene(bumper);

        rootPane.getChildren().remove(bumper);
        if (boardBounds.contains(bumperBounds)) {
            bumper.setTranslateX(0);
            bumper.setTranslateY(0);
            board.add(bumper, board.getCurCol(), board.getCurRow());
            bumper.setMouseTransparent(false);
        }
    }

    public void onBumperReleased(MouseEvent mouseEvent) {
        Bounds boardBounds = getBoundsInScene(board);
        Bounds bumperBounds = getBoundsInScene(bumper);

            GridPane.setColumnIndex(bumper,board.getCurCol());
            GridPane.setRowIndex(bumper,board.getCurRow());
            bumper.setTranslateX(0);
            bumper.setTranslateY(0);

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
}
