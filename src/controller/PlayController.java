package controller;

import controller.handlers.DoNothingHandler;
import controller.handlers.SwitchModeHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.GizmoballModel;
import view.BoardView;

import static util.Constants.MILLIS_PER_FRAME;

public class PlayController {

    private BoardController boardController;
    private DoNothingHandler doNothingHandler;
    private GizmoballModel model;
    private Timeline timeline;

    public BorderPane playRoot;
    @FXML
    private Button switchButton;
    @FXML
    private BoardView board;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    private SwitchModeHandler switchToBuild;

    public void setup(GizmoballModel model, BoardController boardController) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();

        this.timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),  //keyframe that has duration depending on framerate, or it happens immediately
                        actionEvent -> model.moveBalls()
                )
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        playRoot.setCenter(boardController.getBoardView());

        startButton.setOnAction(event -> {
            timeline.play();
        });
        stopButton.setOnAction(event -> {
            timeline.stop();
        });

        switchButton.setOnAction(switchToBuild);
    }

    public void setSwitchHandler(SwitchModeHandler switchToBuild) {
        this.switchToBuild = switchToBuild;
    }

    public void setDoNothing(boolean doNothing) {
        if (doNothing) {
            playRoot.addEventFilter(Event.ANY, doNothingHandler);
        }
        else {
            playRoot.removeEventFilter(Event.ANY, doNothingHandler);
        }
    }

    public Pane getRoot() {
        return playRoot;
    }
}
