package controller;

import controller.handlers.DoNothingHandler;
import controller.handlers.SwitchModeHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.GizmoballModel;
import view.*;

public class BuildController {
    @FXML
    private BorderPane buildRoot;
    @FXML
    private Button switchButton;
    @FXML
    private Button toggleGrid;
    @FXML
    private Slider frictionSlider;
    @FXML
    private TextField frictionField;
    @FXML
    private Slider gravitySlider;
    @FXML
    private TextField gravityField;
    @FXML
    private Slider ballSpeedSlider;
    @FXML
    private TextField ballSpeedField;
    @FXML
    private SquareBumperView squareButton;
    @FXML
    private TriangularBumperView triangleButton;
    @FXML
    private CircularBumperView circleButton;
    @FXML
    private FlipperView leftFlipperButton;
    @FXML
    private FlipperView rightFlipperButton;
    @FXML
    private AbsorberView absorberButton;
    @FXML
    private BallView ballButton;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button clearBoardButton;
    @FXML
    private Button moveButton;
    @FXML
    private Button rotateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label information;

    private GizmoballModel model;
    private BoardController boardController;
    private DoNothingHandler doNothingHandler;
    private SwitchModeHandler switchToPlay;

    public void setup(GizmoballModel model, BoardController boardController) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();

        buildRoot.setCenter(boardController.getBoardView());

        toggleGrid.setOnAction(event -> {
            boardController.getBoardView().toggleGrid();
        });

        switchButton.setOnAction(switchToPlay);

        ballSpeedField.textProperty().bindBidirectional(ballSpeedSlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().bindBidirectional(gravitySlider.valueProperty(), new NumberStringConverter());
        frictionField.textProperty().bindBidirectional(frictionSlider.valueProperty(), new NumberStringConverter());

        squareButton.setOnMouseClicked(event -> {
            squareButton.toggle();
            squareButton.requestFocus();
        });
    }

    public void setSwitchHandler(SwitchModeHandler switchToPlay) {
       this.switchToPlay = switchToPlay;
    }

    public void setDoNothing(boolean doNothing) {
        if (doNothing) {
            buildRoot.addEventFilter(Event.ANY, doNothingHandler);
        }
        else {
            buildRoot.removeEventFilter(Event.ANY, doNothingHandler);
        }
    }

    public Pane getRoot() {
        return buildRoot;
    }
}
