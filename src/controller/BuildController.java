package controller;

import controller.handlers.boardhandlers.*;
import controller.handlers.buildhandlers.ChangeFrictionMu2Handler;
import controller.handlers.buildhandlers.ChangeFrictionMuHandler;
import controller.handlers.buildhandlers.ChangeGravityHandler;
import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.CollisionEngine;
import model.GizmoballModel;
import view.*;
import view.gizmoviews.*;

import javax.swing.event.DocumentEvent;
import java.util.regex.Pattern;

import static model.board.BoardObjectType.*;

public class BuildController {
    @FXML
    public Slider ballYVelocitySlider;
    @FXML
    public TextField ballYVelocityField;
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
    private Slider ballXVelocitySlider;
    @FXML
    private TextField ballXVelocityField;
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
    private CollisionEngine collisionEngine;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToPlay, Stage stage, CollisionEngine collisionEngine) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToPlay = switchToPlay;
        this.stage = stage;
        this.collisionEngine = collisionEngine;

        buildRoot.setCenter(boardController.getBoardView());

        toggleGrid.setOnAction(event -> boardController.getBoardView().toggleGrid());

        switchButton.setOnAction(this.switchToPlay);

        ballXVelocityField.setTextFormatter(getTextFormatter());
        ballYVelocityField.setTextFormatter(getTextFormatter());
        gravityField.setTextFormatter(getTextFormatter());
        frictionMuField.setTextFormatter(getTextFormatter());
        frictionMu2Field.setTextFormatter(getTextFormatter());

        ballXVelocityField.textProperty().bindBidirectional(ballXVelocitySlider.valueProperty(), new NumberStringConverter());
        ballYVelocityField.textProperty().bindBidirectional(ballYVelocitySlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().bindBidirectional(gravitySlider.valueProperty(), new NumberStringConverter());
        frictionMuField.textProperty().bindBidirectional(frictionMuSlider.valueProperty(), new NumberStringConverter());
        frictionMu2Field.textProperty().bindBidirectional(frictionMu2Slider.valueProperty(), new NumberStringConverter());

        gravityField.textProperty().addListener(new ChangeGravityHandler(model, gravityField));
        frictionMuField.textProperty().addListener(new ChangeFrictionMuHandler(collisionEngine, frictionMuField));
        frictionMu2Field.textProperty().addListener(new ChangeFrictionMu2Handler(model, frictionMu2Field));


        setupHandlers();
    }

    private TextFormatter<Double> getTextFormatter() {
        Pattern pattern = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        StringConverter<Double> stringConverter = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object == null ? "0.0" : Double.toString(object);
            }

            @Override
            public Double fromString(String string) {
                if (string == null) {
                    return 0.0;
                } else {
                    string = string.trim();
                    return string.length() < 1 ? 0.0 : Double.valueOf(string);
                }
            }
        };

        TextFormatter<Double> textFormatter = new TextFormatter<>(stringConverter, 0.0, change -> {
            String newText = change.getControlNewText();
            return pattern.matcher(newText).matches() ? change : null;
        });

        return textFormatter;
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
