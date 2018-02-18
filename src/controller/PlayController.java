package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;
import model.GizmoballModel;
import view.BoardView;

import static util.Constants.MILLIS_PER_FRAME;

public class PlayController {

    private GizmoballModel model;
    public Button switchButton;
    public BoardView board;
    public Button startButton;
    public Button stopButton;
    private Timeline timeline;

    public PlayController() {
    }

    @FXML
    public void initialize() {

        startButton.setOnAction(event -> {
            timeline.play();
        });





    }

    public void setModel(GizmoballModel model) {
        this.model = model;

        timeline = new Timeline(
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
    }

    public void toggleGrid() {
        board.toggleGrid();
    }

    public void setBoard(BoardView board) {
        this.board = board;
    }

    public BoardView getBoard() {
        return board;
    }

    public void setSwitchHandler(SwitchModeHandler switchToBuild) {
        switchButton.setOnAction(switchToBuild);
    }
}
