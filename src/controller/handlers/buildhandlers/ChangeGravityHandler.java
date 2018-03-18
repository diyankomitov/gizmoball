package controller.handlers.buildhandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import model.GizmoballModel;

public class ChangeGravityHandler implements ChangeListener<String>{
    private final GizmoballModel model;

    public ChangeGravityHandler(GizmoballModel model){
        this.model = model;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        if (newValue.equals("")) {
//            observable. = "0";
        }

//        KeyCode


    }
}
