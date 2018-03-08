package controller;

import controller.handlers.boardhandlers.*;
import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;
import model.GizmoballModel;
import model.board.BoardObjectType;
import view.*;

import static model.board.BoardObjectType.*;

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

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToPlay) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToPlay = switchToPlay;

        buildRoot.setCenter(boardController.getBoardView());

        toggleGrid.setOnAction(event -> boardController.getBoardView().toggleGrid());

        switchButton.setOnAction(this.switchToPlay);

        ballSpeedField.textProperty().bindBidirectional(ballSpeedSlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().bindBidirectional(gravitySlider.valueProperty(), new NumberStringConverter());
        frictionField.textProperty().bindBidirectional(frictionSlider.valueProperty(), new NumberStringConverter());

        setupHandlers();
    }

    private void setupHandlers() {

        squareButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(SQUARE)));
        triangleButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(TRIANGLE)));
        circleButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(CIRCLE)));
        leftFlipperButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(LEFT_FLIPPER)));
        rightFlipperButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(RIGHT_FLIPPER)));
        absorberButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddAbsorberHandler()));
        ballButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddBallHandler()));

        moveButton.setOnAction(event -> boardController.setBoardHandler(new MoveHandler()));
        rotateButton.setOnAction(event -> boardController.setBoardHandler(new RotateHandler()));
        deleteButton.setOnAction(event -> boardController.setBoardHandler(new DeleteHandler()));
        connectButton.setOnAction(event -> boardController.setBoardHandler(new ConnectTriggerHandler()));
        disconnectButton.setOnAction(event -> boardController.setBoardHandler(new DisconnectTriggerHandler()));
        clearBoardButton.setOnAction(event -> boardController.setBoardHandler(new ClearBoardHandler()));
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

    public void toggleBoard() {
        buildRoot.setCenter(boardController.getBoardView());
        boardController.getBoardView().toggleGrid();
    }
}
