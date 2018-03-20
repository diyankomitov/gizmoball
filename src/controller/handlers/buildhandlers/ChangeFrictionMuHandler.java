package controller.handlers.buildhandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import model.CollisionEngine;
import model.GizmoballModel;

public class ChangeFrictionMuHandler implements ChangeListener<String> {
    private final CollisionEngine collisionEngine;
    private TextField textField;

    public ChangeFrictionMuHandler(CollisionEngine collisionEngine, TextField textField) {
        this.collisionEngine = collisionEngine;
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

        collisionEngine.setFrictionMU(newDouble);
    }
}
