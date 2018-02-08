package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import view.*;

public class BuildController {
    public Button switchButton;
    public BoardView board;
    public Button toggleGrid;
    public Slider frictionSlider;
    public TextField frictionField;
    public Slider gravitySlider;
    public TextField gravityField;
    public Slider ballSpeedSlider;
    public TextField ballSpeedField;
    public SquareBumperView squareButton;
    public TriangularBumperView triangleButton;
    public CircularBumperView circleButton;
    public FlipperView leftFlipperButton;
    public FlipperView rightFlipperButton;
    public AbsorberView absorberButton;
    public BallView ballButton;
    public Button connectButton;
    public Button disconnectButton;
    public Button clearBoardButton;
    public Button moveButton;
    public Button rotateButton;
    public Button deleteButton;

    public BuildController() {
    }

    @FXML
    public void initialize() {
        toggleGrid.setOnAction(event -> {
            board.toggleGrid();
        });

        ballSpeedField.textProperty().bindBidirectional(ballSpeedSlider.valueProperty(), new NumberStringConverter());
        gravityField.textProperty().bindBidirectional(gravitySlider.valueProperty(), new NumberStringConverter());
        frictionField.textProperty().bindBidirectional(frictionSlider.valueProperty(), new NumberStringConverter());

        squareButton.setOnMouseClicked(event -> {
            squareButton.toggle();
            squareButton.requestFocus();
        });
    }

    public void setSwitchHandler(SwitchModeHandler switchToPlay) {
        switchButton.setOnAction(switchToPlay);
    }
}
