package controller.handlers.buildhandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import model.GizmoballModel;

public class ChangeFrictionMu2Handler implements ChangeListener<String> {
    private final GizmoballModel model;
    private TextField textField;

    public ChangeFrictionMu2Handler(GizmoballModel model, TextField textField) {
        this.model = model;
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Double newDouble = Double.parseDouble(newValue);
        if (newDouble > 1000) {
            newDouble = 1000.0;
            textField.setText("1000");
        }
        else if (newDouble < -1000) {
            newDouble = -1000.0;
            textField.setText("-1000");
        }

        model.getCollisionEngine().setFrictionMU2(newDouble);

    }
}
