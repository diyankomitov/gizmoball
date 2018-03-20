package controller.handlers.buildhandlers;

import controller.BuildController;
import controller.handlers.boardhandlers.AddBallHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import model.GizmoballModel;
import model.board.Ball;

public class ChangeBallXVelocityHandler implements ChangeListener<String> {
    private final GizmoballModel model;
    private TextField textField;
    private BuildController buildController;
    private AddBallHandler addBallHandler;

    public ChangeBallXVelocityHandler(GizmoballModel model, TextField textField, BuildController buildController) {
        this.model = model;
        this.textField = textField;
        this.buildController = buildController;
    }

    public void setAddBallHandler(AddBallHandler addBallHandler) {
        this.addBallHandler = addBallHandler;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        Double newDouble;
        if (newValue.equals(".") || newValue.equals("")) {
            newDouble = 0.0;
        }
        else {
            newDouble = Double.parseDouble(newValue);
        }

        if (newDouble > 0.0 && newDouble < 0.01) {
            textField.setText("0.01");
            newDouble = 0.1;
        }
        else if (newDouble < 0.0 && newDouble > -0.01) {
            textField.setText("-0.01");
            newDouble = -0.1;
        }
        else if (newDouble > 200) {
            newDouble = 200.0;
            textField.setText("200");
        }
        else if (newDouble < -200) {
            newDouble = -200.0;
            textField.setText("-200");
        }

        Ball selectedBall = buildController.getSelecedBall();
        if (buildController.isAddBallSelected()) {
            addBallHandler.setVx(newDouble);
        }

        if (selectedBall != null && !buildController.isAddBallSelected()) {
            selectedBall.setXVelocity(newDouble);
        }
        else {
            for (Ball ball : model.getBalls()) {
                ball.setXVelocity(newDouble);
            }
        }
    }
}