package controller.handlers.buildhandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import model.GizmoballModel;
import model.board.Ball;

public class ChangeBallYVelocityHandler implements ChangeListener<String> {
    private final GizmoballModel model;
    private TextField textField;

    public ChangeBallYVelocityHandler(GizmoballModel model, TextField textField) {
        this.model = model;
        this.textField = textField;
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

        for (Ball ball : model.getBalls()) {
            ball.setYVelocity(newDouble);
        }
    }
}
