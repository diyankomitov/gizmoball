package controller.handlers.runhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GizmoballModel;

public class ResetGameHandler implements EventHandler<ActionEvent> {
    private final GizmoballModel model;

    public ResetGameHandler(GizmoballModel model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
