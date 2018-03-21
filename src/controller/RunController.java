package controller;

import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.LoadHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import controller.handlers.runhandlers.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GizmoballModel;

import static util.Constants.MILLIS_PER_FRAME;

public class RunController {

    @FXML
    private Button tickButton;
    @FXML
    private Button resetButton;
    @FXML
    private BorderPane playRoot;
    @FXML
    private Button switchButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    private TriggerHandler triggerHandler;
    private BoardController boardController;
    private DoNothingHandler doNothingHandler;
    private Timeline timeline;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToBuild, Stage stage, LoadHandler loadHandler) {
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();

        triggerHandler = new TriggerHandler();

        this.timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),  //keyframe that has duration depending on framerate, or it happens immediately
                        actionEvent -> model.moveBalls()
                )
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        stopButton.setDisable(true);

        startButton.setFocusTraversable(false);
        stopButton.setFocusTraversable(false);
        tickButton.setFocusTraversable(false);
        switchButton.setFocusTraversable(false);
        resetButton.setFocusTraversable(false);

        startButton.setOnAction(event -> {
            GizmoballController.disable.set(true);
            stopButton.requestFocus();
            new StartLoopHandler(timeline, boardController.getBoardView()).handle(event);
        });
        stopButton.setOnAction(event -> {
            GizmoballController.disable.set(false);
            startButton.requestFocus();
            new StopLoopHandler(timeline).handle(event);
        });
        resetButton.setOnAction(new ResetGameHandler(model, loadHandler));
        tickButton.setOnAction(new TickLoopHandler(model));
        switchButton.setOnAction(switchToBuild);

        startButton.disableProperty().bind(GizmoballController.disable);
        stopButton.disableProperty().bind(GizmoballController.disable.not());
        resetButton.disableProperty().bind(GizmoballController.disable);
        tickButton.disableProperty().bind(GizmoballController.disable);
        switchButton.disableProperty().bind(GizmoballController.disable);
    }

    public void setDoNothing(boolean doNothing) {
        if (doNothing) {
            playRoot.addEventFilter(Event.ANY, doNothingHandler);
            boardController.getBoardView().removeEventHandler(KeyEvent.ANY, triggerHandler);
        }
        else {
            playRoot.removeEventFilter(Event.ANY, doNothingHandler);
            boardController.getBoardView().addEventHandler(KeyEvent.ANY, triggerHandler);
        }
    }

    public Pane getRoot() {
        return playRoot;
    }

    public void toggleBoard() {
        playRoot.setCenter(boardController.getBoardView());
        boardController.getBoardView().setGrid(false);
    }
}
