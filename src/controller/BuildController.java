package controller;

import controller.handlers.boardhandlers.*;
import controller.handlers.buildhandlers.*;
import controller.handlers.generalhandlers.DoNothingHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.CollisionEngine;
import model.GizmoballModel;
import model.board.Ball;
import view.*;
import view.gizmoviews.*;

import java.util.regex.Pattern;

import static model.board.BoardObjectType.*;

public class BuildController {
    @FXML
    public Slider ballYVelocitySlider;
    @FXML
    public TextField ballYVelocityField;
    @FXML
    public ToggleButton selectBallButton;
    @FXML
    public CheckBox globalCheckBox;
    @FXML
    public VBox velocityContainer;
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
    private ToggleButton connectButton;
    @FXML
    private ToggleButton disconnectButton;
    @FXML
    private Button clearBoardButton;
    @FXML
    private ToggleButton moveButton;
    @FXML
    private ToggleButton rotateButton;
    @FXML
    private ToggleButton deleteButton;
    @FXML
    private Label information;

    private GizmoballModel model;
    private BoardController boardController;
    private DoNothingHandler doNothingHandler;
    private SwitchModeHandler switchToPlay;
    private Stage stage;
    private Ball selectedBall;
    private ToggleGroup toggleGroup;
    private AddBallHandler addBallHandler;
    private boolean addBallSelected;
    private ChangeBallXVelocityHandler changeBallXVelocityHandler;
    private ChangeBallYVelocityHandler changeBallYVelocityHandler;

    public void setup(GizmoballModel model, BoardController boardController, SwitchModeHandler switchToPlay, Stage stage, CollisionEngine collisionEngine) {
        this.model = model;
        this.boardController = boardController;
        this.doNothingHandler = new DoNothingHandler();
        this.switchToPlay = switchToPlay;
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();

        buildRoot.setCenter(boardController.getBoardView());

        toggleGrid.setOnAction(event -> boardController.getBoardView().toggleGrid());

        switchButton.setOnAction(event -> {
            toggleGroup.selectToggle(null);
            this.switchToPlay.handle(event);
        });

        buildRoot.setFocusTraversable(false);

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

        changeBallXVelocityHandler = new ChangeBallXVelocityHandler(model, ballXVelocityField, this);
        changeBallYVelocityHandler = new ChangeBallYVelocityHandler(model, ballYVelocityField, this);
        ballXVelocityField.textProperty().addListener(changeBallXVelocityHandler);
        ballYVelocityField.textProperty().addListener(changeBallYVelocityHandler);
        gravityField.textProperty().addListener(new ChangeGravityHandler(model, gravityField));
        frictionMuField.textProperty().addListener(new ChangeFrictionMuHandler(collisionEngine, frictionMuField));
        frictionMu2Field.textProperty().addListener(new ChangeFrictionMu2Handler(model, frictionMu2Field));

//        gravityField.textProperty().bind(Bindings.convert(model.getGravityProperty()));
//        frictionMuField.textProperty().bind(Bindings.convert(model.getFrictionMUProperty()));
//        frictionMu2Field.textProperty().bind(Bindings.convert(model.getFrictionMU2Property()));


        globalCheckBox.setOnAction(event -> {
            if (globalCheckBox.isSelected()) {
                velocityContainer.setDisable(false);
                selectBallButton.setDisable(true);
                if (toggleGroup.getSelectedToggle() != null && toggleGroup.getSelectedToggle().equals(selectBallButton)){
                    toggleGroup.selectToggle(null);
                }
                for (Ball ball : model.getBalls()) {
                    ball.setGlobalVelocity(true);
                }
                addBallHandler.setGlobal(true);
            }
            else {
                if (!ballButton.isSelected()) {
                    velocityContainer.setDisable(true);
                }
                selectBallButton.setDisable(false);
                for (Ball ball : model.getBalls()) {
                    ball.setGlobalVelocity(false);
                }
            }
        });

        selectBallButton.setOnAction(event -> boardController.setBoardHandler(new SelectBallHandler(model, this, selectBallButton)));
        selectBallButton.setToggleGroup(toggleGroup);


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

    private void toggleButton(Toggle toggle) {
        if (toggle.isSelected()) {
            toggleGroup.selectToggle(null);
        }
        else {
            toggleGroup.selectToggle(toggle);
        }
    }

    private void setupHandlers() {
        squareButton.setOnMouseClicked(event -> {
            toggleButton(squareButton);
            boardController.setBoardHandler(new AddHandler(model, boardController,this, SQUARE, information));
        });
        triangleButton.setOnMouseClicked(event -> {
            toggleButton(triangleButton);
            boardController.setBoardHandler(new AddHandler(model, boardController,this, TRIANGLE, information));

        });
        circleButton.setOnMouseClicked(event -> {
            toggleButton(circleButton);
            boardController.setBoardHandler(new AddHandler(model, boardController,this, CIRCLE, information));
        });
        leftFlipperButton.setOnMouseClicked(event -> {
            toggleButton(leftFlipperButton);
            boardController.setBoardHandler(new AddHandler(model, boardController,this, LEFT_FLIPPER, information));
        });
        rightFlipperButton.setOnMouseClicked(event -> {
            toggleButton(rightFlipperButton);
            boardController.setBoardHandler(new AddHandler(model, boardController,this, RIGHT_FLIPPER, information));
        });
        absorberButton.setOnMouseClicked(event -> {
            toggleButton(absorberButton);
            boardController.setBoardHandler(new AddAbsorberHandler(model, boardController, this, information));
        });
        ballButton.setOnMouseClicked(event -> {
            toggleButton(ballButton);
            addBallHandler = new AddBallHandler(model, boardController, this, information);
            boardController.setBoardHandler(addBallHandler);
            changeBallXVelocityHandler.setAddBallHandler(addBallHandler);
            changeBallYVelocityHandler.setAddBallHandler(addBallHandler);
        });

        ballButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addBallSelected = true;
                velocityContainer.setDisable(false);
                System.out.println("is add a null" + addBallHandler);
            }
            else {
                addBallSelected = false;
                if (!globalCheckBox.isSelected()) {
                    velocityContainer.setDisable(true);
                }
            }
        });

        moveButton.setOnAction(event -> {
            if (!moveButton.isSelected()) {
                boardController.setDoNothing();
            }
            else {
                boardController.setBoardHandler(new MoveHandler(model, moveButton));
            }
        });
        rotateButton.setOnAction(event -> {
            if (!rotateButton.isSelected()) {
                boardController.setDoNothing();
            }
            else {
                boardController.setBoardHandler(new RotateHandler(model, this));
            }
        });
        deleteButton.setOnAction(event -> {
            if (!deleteButton.isSelected()) {
                boardController.setDoNothing();
            }
            else {
                boardController.setBoardHandler(new DeleteHandler(boardController, model));
            }
        });
        connectButton.setOnAction(event -> {
            if (!connectButton.isSelected()) {
                boardController.setDoNothing();
            }
            else {
                boardController.setBoardHandler(new ConnectTriggerHandler(model, boardController, stage, information, connectButton));
            }
        });
        disconnectButton.setOnAction(event -> {
            if (!disconnectButton.isSelected()) {
                boardController.setDoNothing();
            }
            else {
                boardController.setBoardHandler(new DisconnectTriggerHandler(model, information));
            }
        });

        clearBoardButton.setOnAction(event -> {
            boardController.setBoardHandler(new ClearBoardHandler(boardController, model, information));
            boardController.handle(event);
            boardController.setDoNothing();
            toggleGroup.selectToggle(null);
        });

        moveButton.setToggleGroup(toggleGroup);
        rotateButton.setToggleGroup(toggleGroup);
        deleteButton.setToggleGroup(toggleGroup);
        connectButton.setToggleGroup(toggleGroup);
        disconnectButton.setToggleGroup(toggleGroup);

        squareButton.setToggleGroup(toggleGroup);
        triangleButton.setToggleGroup(toggleGroup);
        circleButton.setToggleGroup(toggleGroup);
        leftFlipperButton.setToggleGroup(toggleGroup);
        rightFlipperButton.setToggleGroup(toggleGroup);
        absorberButton.setToggleGroup(toggleGroup);
        ballButton.setToggleGroup(toggleGroup);
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

    public void setSelectedBall(Ball selectedBall) {
        this.selectedBall = selectedBall;
        if (selectedBall == null) {
            velocityContainer.setDisable(true);
        }
        else {
            velocityContainer.setDisable(false);
        }
    }
    public Ball getSelecedBall() {
        return selectedBall;
    }

    public boolean isAddBallSelected() {
        return addBallSelected;
    }
}
