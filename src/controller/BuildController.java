package controller;

import controller.handlers.boardhandlers.*;
import controller.handlers.buildhandlers.ChangeGravityHandler;
import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.GizmoballModel;
import view.*;
import view.gizmoviews.*;

import static model.board.BoardObjectType.*;

public class BuildController {
    @FXML
    private BorderPane buildRoot;
    @FXML
    private Button switchButton;
    @FXML
    private Button toggleGrid;
    @FXML
    private Slider frictionMuSlider;
    @FXML
    private TextField frictionMuField;
    @FXML
    private Slider frictionMu2Slider;
    @FXML
    private TextField frictionMu2Field;
    @FXML
    private Slider gravitySlider;
    @FXML
    private TextField gravityField;
    @FXML
    private Slider ballSpeedSlider;
    @FXML
    private TextField ballSpeedField;
    @FXML
    private SquareGizmoView squareButton;
    @FXML
    private TriangleGizmoView triangleButton;
    @FXML
    private CircleGizmoView circleButton;
    @FXML
    private FlipperGizmoView leftFlipperButton;
    @FXML
    private FlipperGizmoView rightFlipperButton;
    @FXML
    private AbsorberGizmoView absorberButton;
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
    private Stage stage;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToPlay, Stage stage) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToPlay = switchToPlay;
        this.stage = stage;

        buildRoot.setCenter(boardController.getBoardView());

        toggleGrid.setOnAction(event -> boardController.getBoardView().toggleGrid());

        switchButton.setOnAction(this.switchToPlay);

        ballSpeedField.textProperty().bindBidirectional(ballSpeedSlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().bindBidirectional(gravitySlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().addListener(new ChangeGravityHandler(model));
        frictionMuField.textProperty().bindBidirectional(frictionMuSlider.valueProperty(), new NumberStringConverter());
        frictionMu2Field.textProperty().bindBidirectional(frictionMu2Slider.valueProperty(), new NumberStringConverter());

        setupHandlers();
    }

    private void setupHandlers() {

        squareButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(model, boardController,this, SQUARE, information)));
        triangleButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(model, boardController,this, TRIANGLE, information)));
        circleButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(model, boardController,this, CIRCLE, information)));
        leftFlipperButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(model, boardController,this, LEFT_FLIPPER, information)));
        rightFlipperButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddHandler(model, boardController,this, RIGHT_FLIPPER, information)));
        absorberButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddAbsorberHandler(model, boardController, this, information)));
        ballButton.setOnMouseClicked(event -> boardController.setBoardHandler(new AddBallHandler(model, boardController, this, information)));

        moveButton.setOnAction(event -> boardController.setBoardHandler(new MoveHandler(model)));
        rotateButton.setOnAction(event -> boardController.setBoardHandler(new RotateHandler(model, this)));
        deleteButton.setOnAction(event -> boardController.setBoardHandler(new DeleteHandler(boardController, model)));
        connectButton.setOnAction(event -> boardController.setBoardHandler(new ConnectTriggerHandler(model, boardController, stage, information)));
        disconnectButton.setOnAction(event -> boardController.setBoardHandler(new DisconnectTriggerHandler(model, information)));
        clearBoardButton.setOnAction(event -> {
            boardController.setBoardHandler(new ClearBoardHandler(boardController, model, information));
            boardController.handle(event);
            boardController.setDoNothing();
        });
    }

    public void setDoNothing(boolean doNothing) {
        if (doNothing) {
            buildRoot.addEventFilter(Event.ANY, doNothingHandler);

        }
        else {
            buildRoot.removeEventFilter(Event.ANY, doNothingHandler);
        }

        boardController.setDoNothing();
    }

    public void setInformation(String text) {
        information.setText(text);

    }

    public Label getInfoLabel(){
        return information;
    }
    public Pane getRoot() {
        return buildRoot;
    }

    public void toggleBoard() {
        buildRoot.setCenter(boardController.getBoardView());
        boardController.getBoardView().setGrid(true);
    }
}
