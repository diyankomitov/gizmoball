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
import view.BoardView;

import static util.Constants.MILLIS_PER_FRAME;

public class RunController {

    @FXML
    public Button tickButton;
    @FXML
    public Button resetButton;
    @FXML
    public Pane boardPaneWrapper;

    @FXML
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
    private Stage stage;
    private TriggerHandler triggerHandler;
    private LoadHandler loadHandler;
    private BoardController boardController;
    private DoNothingHandler doNothingHandler;
    private GizmoballModel model;
    private Timeline timeline;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToBuild, Stage stage, LoadHandler loadHandler) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToBuild = switchToBuild;
        this.stage = stage;
        this.loadHandler = loadHandler;

        triggerHandler = new TriggerHandler();

        this.timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),  //keyframe that has duration depending on framerate, or it happens immediately
                        actionEvent -> model.moveBalls()
                )
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        stopButton.setDisable(true);

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
        switchButton.setOnAction(this.switchToBuild);

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
