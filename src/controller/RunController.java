package controller;

import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import controller.handlers.runhandlers.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    private Stage stage;
    private TriggerHandler triggerHandler;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToBuild, Stage stage) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToBuild = switchToBuild;
        this.stage = stage;

        triggerHandler = new TriggerHandler();

        this.timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.millis(MILLIS_PER_FRAME),  //keyframe that has duration depending on framerate, or it happens immediately
                        actionEvent -> model.moveBalls()
                )
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called

        startButton.setOnAction(new StartLoopHandler(timeline, boardController.getBoardView()));
        stopButton.setOnAction(new StopLoopHandler(timeline));
        resetButton.setOnAction(new ResetGameHandler(model));
        tickButton.setOnAction(new TickLoopHandler(model));
        switchButton.setOnAction(this.switchToBuild);
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
